package org.book_my_show.domain.booking;

import jakarta.persistence.*;
import lombok.Getter;
import org.book_my_show.domain.BaseEntity;
import org.book_my_show.domain.show.Show;
import org.book_my_show.domain.show.ShowSeat;
import org.book_my_show.domain.user.User;
import org.book_my_show.domain.status.BookingStatus;
import org.book_my_show.domain.status.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "bookings")
@Getter
public class Booking extends BaseEntity {
    @Column(unique = true)
    private String bookingReference;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "show_id", nullable = false)
    private Show show;

    @ElementCollection
    @CollectionTable(name = "booking_seats", joinColumns = @JoinColumn(name = "booking_id"))
    private Set<BookingSeat> seats;

    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;

    private LocalDateTime bookingTime;

    @Column(precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    private LocalDateTime showTime;

    public Booking() {
        this.seats = new HashSet<>();
        this.bookingStatus = BookingStatus.INITIATED;
        this.paymentStatus = PaymentStatus.PENDING;
    }

    public static Booking createBooking(User user, Show show, LocalDateTime showTime, Set<BookingSeat> seats){
        Booking booking = new Booking();
        booking.user=user;
        booking.seats=seats;
        booking.showTime=showTime;
        booking.show=show;
        booking.updateTotalAmount();
        return booking;
    }

    private void updateTotalAmount() {
        this.totalAmount = seats.stream()
                .map(BookingSeat::getPriceCharged)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static Booking initiateBooking(User user, Show show,
                                          Set<ShowSeat> selectedSeats, LocalDateTime showTime, String discountCode) {
        Objects.requireNonNull(user, "User cannot be null");
        Objects.requireNonNull(show, "Show cannot be null");
        Objects.requireNonNull(selectedSeats, "Selected seats cannot be null");
        Objects.requireNonNull(showTime, "Show time cannot be null");

        validateBookingRequest(show, selectedSeats, showTime);

        Booking booking = new Booking();
        booking.user = user;
        booking.show = show;
        booking.showTime = showTime;
        booking.bookingTime = LocalDateTime.now();
        booking.bookingStatus = BookingStatus.INITIATED;
        booking.bookingReference = generateBookingReference();
        booking.updateTotalAmount();
        booking.seats = selectedSeats.stream()
                .map(seat -> BookingSeat.create(
                        seat.book(seat.getPriceWithPremium()),
                        seat.getPriceWithPremium(),
                        BigDecimal.ZERO,
                        discountCode))
                .collect(Collectors.toSet());


        return booking;
    }

    private static String generateBookingReference(){ return UUID.randomUUID().toString();}

    private static void validateBookingRequest(Show show,
                                               Set<ShowSeat> selectedSeats, LocalDateTime showTime) {
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);

        if (show.getShowTime().isBefore(now)) {
            throw new IllegalArgumentException("Cannot book for past show");
        }

        if (!selectedSeats.stream().allMatch(ShowSeat::isAvailable)) {
            throw new IllegalArgumentException("Some selected seats are not available");
        }
    }

    public void confirm(Payment payment) {
        if (bookingStatus != BookingStatus.INITIATED) {
            throw new IllegalStateException("Booking is not in INITIATED state");
        }
        BigDecimal amt = payment.getAmount();
        if (payment.getAmount().compareTo(amt) != 0) {
            throw new IllegalArgumentException("Payment amount does not match booking amount");
        }

        this.bookingStatus = BookingStatus.CONFIRMED;
        this.paymentStatus = PaymentStatus.COMPLETED;
    }

    public void setUser(User user){ this.user=user; }

    public void setShow(Show show){ this.show=show; }

    public void updateBookingAmount(BigDecimal totalAmount){
        this.totalAmount = totalAmount;
    }
}
