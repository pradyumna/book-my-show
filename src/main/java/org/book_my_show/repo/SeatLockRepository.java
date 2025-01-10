package org.book_my_show.repo;

import org.book_my_show.domain.seat_lock.SeatLock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SeatLockRepository extends JpaRepository<SeatLock, Long> {

    @Query("SELECT sl FROM SeatLock sl WHERE sl.showId = :showId AND sl.seatNumber IN :seatNumber")
    List<SeatLock> findLocksForSeats(@Param("showId") Long showId, @Param("seatNumber") List<String> seatNumbers);

    @Modifying
    @Query("DELETE FROM SeatLock sl WHERE sl.showId = :showId AND sl.seatNumber IN :seatNumber AND sl.userId = :userId")
    void releaseSeats(@Param("showId") Long showId, @Param("seatNumber") List<String> seatNumbers, @Param("userId") Long userId);

    @Modifying
    @Query("DELETE FROM SeatLock sl WHERE sl.lockedUntil < :now")
    void cleanupExpiredLocks(LocalDateTime now);
}
