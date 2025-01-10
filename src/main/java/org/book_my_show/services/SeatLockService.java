package org.book_my_show.services;

import lombok.extern.slf4j.Slf4j;
import org.book_my_show.domain.seat_lock.SeatLock;
import org.book_my_show.domain.show.Show;
import org.book_my_show.domain.user.User;
import org.book_my_show.exceptions.SeatUnavailableException;
import org.book_my_show.repo.SeatLockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class SeatLockService {
    private static final Duration LOCK_DURATION = Duration.ofMinutes(10);

    @Autowired
    private SeatLockRepository seatLockRepository;

    public void lockSeats(Show show, List<String> seatNumbers, User user) {
        log.info("Attempting to lock seats {} for show {} by user {}",
                seatNumbers, show.getId(), user.getId());

        // Check existing locks
        List<SeatLock> existingLocks = seatLockRepository.findLocksForSeats(show.getId(), seatNumbers);

        validateSeatsAvailability(existingLocks, seatNumbers);

        try {
            // Create new locks
            List<SeatLock> newLocks = seatNumbers.stream()
                    .map(seatNumber -> new SeatLock(
                            show.getId(),
                            seatNumber,
                            user.getId(),
                            LOCK_DURATION))
                    .collect(Collectors.toList());

            seatLockRepository.saveAll(newLocks);

            log.info("Successfully locked seats {} for show {} by user {}",
                    seatNumbers, show.getId(), user.getId());

        } catch (DataIntegrityViolationException e) {
            log.error("Concurrent lock attempt detected for seats {} in show {}",
                    seatNumbers, show.getId());
            throw new SeatUnavailableException("Seats are already locked");
        }
    }

    private void validateSeatsAvailability(List<SeatLock> existingLocks,
                                           List<String> requestedSeats) {
        List<String> unavailableSeats = existingLocks.stream()
                .filter(lock -> !lock.isLockExpired())
                .map(SeatLock::getSeatNumber)
                .collect(Collectors.toList());

        if (!unavailableSeats.isEmpty()) {
            log.warn("Seats {} are already locked", unavailableSeats);
            throw new SeatUnavailableException(
                    "Seats " + unavailableSeats + " are not available");
        }
    }

    public void unlockSeats(Show show, List<String> seatNumbers, User user) {
        log.info("Releasing locks for seats {} in show {} by user {}",
                seatNumbers, show.getId(), user.getId());

        seatLockRepository.releaseSeats(show.getId(), seatNumbers, user.getId());
    }

    @Scheduled(fixedRate = 60000) // Run every minute
    @Transactional
    public void cleanupExpiredLocks() {
        log.debug("Running expired locks cleanup");
        seatLockRepository.cleanupExpiredLocks(LocalDateTime.now());
    }
}
