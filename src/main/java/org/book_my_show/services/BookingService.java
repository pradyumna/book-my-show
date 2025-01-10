package org.book_my_show.services;

import lombok.RequiredArgsConstructor;
import org.book_my_show.domain.booking.Booking;
import org.book_my_show.domain.booking.BookingSeat;
import org.book_my_show.domain.booking.Payment;
import org.book_my_show.domain.show.Show;
import org.book_my_show.domain.show.ShowSeat;
import org.book_my_show.domain.status.PaymentStatus;
import org.book_my_show.domain.user.User;
import org.book_my_show.dto.*;
import org.book_my_show.exceptions.UserNotFoundException;
import org.book_my_show.mappers.BookingMapper;
import org.book_my_show.mappers.BookingSeatMapper;
import org.book_my_show.repo.BookingRepository;
import org.book_my_show.repo.ShowRepository;
import org.book_my_show.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingMapper bookingMapper;
    private final BookingRepository bookingRepository;
    private final SeatLockService seatLockService;
    private final UserRepository userRepository;
    private final ShowRepository showRepository;
    private final BookingSeatMapper bookingSeatMapper;

    public BookingDTO bookMovieTicket(BookingRequestDTO requestDTO) {

        if (Objects.isNull(requestDTO.getSeats())) {
            throw new IllegalArgumentException("Found empty/null seats while booking");
        }


        Optional<User> userOpt = userRepository.findById(requestDTO.getUserId());
        Optional<Show> showOpt = showRepository.findById(requestDTO.getShowId());
        Show show = showOpt.orElseThrow(() -> new Show.SeatNotFoundException("Show seat not found"));
        User user = userOpt.orElseThrow(() -> new UserNotFoundException("User id not found"));

        BookingDTO bookingDTO;
        List<String> seatNos = requestDTO.getSeats().stream().map(BookingSeatRequestDTO::getShowSeatId).collect(Collectors.toList());
        seatLockService.lockSeats(show, seatNos, user);
        try {
            Set<BookingSeat> processedSeats = requestDTO.getSeats().stream()
                    .map(seatRequest -> processBookingSeat(show, user, seatNos,seatRequest))
                    .collect(Collectors.toSet());

            Optional<Booking> bookingOpt = bookingRepository.findByShowIdAndSeats(show,processedSeats);

            Set<ShowSeat> showSeats = processedSeats.stream().map(BookingSeat::getShowSeat).collect(Collectors.toSet());
            Booking booking = Booking.initiateBooking(user,show,showSeats,show.getShowTime(), null);

            booking.confirm(Payment.createPayment());
            booking = bookingRepository.save(booking);
            bookingDTO = bookingMapper.toDto(booking);
        } finally {
            seatLockService.unlockSeats(show, seatNos, user);
        }

        return bookingDTO;
    }

    private BookingSeat processBookingSeat(Show show, User user, List<String> seatNos, BookingSeatRequestDTO seatRequest) {
        ShowSeat showSeat = show.findSeat(seatRequest.getShowSeatId())
                .orElseThrow(() -> new Show.SeatNotFoundException(seatRequest.getShowSeatId()));

        BigDecimal originalPrice = showSeat.getPriceWithPremium();
        return BookingSeat.create(showSeat, originalPrice, BigDecimal.ZERO, null);
    }
}
