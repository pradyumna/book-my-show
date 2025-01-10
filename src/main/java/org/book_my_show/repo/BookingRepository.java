package org.book_my_show.repo;

import org.book_my_show.domain.booking.Booking;
import org.book_my_show.domain.booking.BookingSeat;
import org.book_my_show.domain.show.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    Optional<Booking> findByShowIdAndSeats(Show show, Set<BookingSeat> seats);
}
