package org.book_my_show.domain.show;

import jakarta.persistence.*;
import lombok.Getter;
import org.book_my_show.domain.screen.SeatConfiguration;
import org.book_my_show.domain.status.SeatStatus;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

@Embeddable
@Getter
public class ShowSeat{
    @Embedded
    private final SeatConfiguration seat;

    @Enumerated(EnumType.STRING)
    private SeatStatus status;

    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    private LocalDateTime lastStatusUpdate;



    public ShowSeat() {
        this.seat = null;
        this.status = null;
        this.price = null;
        this.lastStatusUpdate = null;
    }

    public static ShowSeat create(SeatConfiguration seat, BigDecimal basePrice) {
        return new ShowSeat(
                Objects.requireNonNull(seat, "Seat configuration cannot be null"),
                SeatStatus.AVAILABLE,
                calculateInitialPrice(seat, basePrice),
                LocalDateTime.now()
        );
    }

    private ShowSeat(SeatConfiguration seat,
                     SeatStatus status,
                     BigDecimal price,
                     LocalDateTime lastStatusUpdate) {
        this.seat = seat;
        this.status = status;
        this.price = price;
        this.lastStatusUpdate = lastStatusUpdate;
    }

    public ShowSeat book(BigDecimal bookingAmount) {
        validateBooking(bookingAmount);
        return new ShowSeat(
                this.seat,
                SeatStatus.BOOKED,
                bookingAmount,
                LocalDateTime.now()
        );
    }

    public ShowSeat lock(Duration lockDuration) {
        if (!isAvailable()) {
            throw new SeatNotAvailableException(seat.getSeatNumber());
        }
        return new ShowSeat(
                this.seat,
                SeatStatus.BLOCKED,
                this.price,
                LocalDateTime.now().plus(lockDuration)
        );
    }

    public ShowSeat release() {
        if (status != SeatStatus.BLOCKED) {
            throw new InvalidSeatStateException("Only locked seats can be released");
        }
        return new ShowSeat(
                this.seat,
                SeatStatus.AVAILABLE,
                this.price,
                LocalDateTime.now()
        );
    }

    public boolean isAvailable() {
        if (status == SeatStatus.BLOCKED) {
            return isLockExpired();
        }
        return status == SeatStatus.AVAILABLE;
    }

    public BigDecimal getPriceWithPremium() {
        return price.multiply(seat.getPricingMultiplier());
    }

    private boolean isLockExpired() {
        return lastStatusUpdate.plus(Duration.ofMinutes(10))
                .isBefore(LocalDateTime.now());
    }

    private void validateBooking(BigDecimal bookingAmount) {
        if (!isAvailable()) {
            throw new SeatNotAvailableException(seat.getSeatNumber());
        }
        if (bookingAmount.compareTo(price) < 0) {
            throw new InvalidPriceException("Booking amount cannot be less than seat price");
        }
    }

    private static BigDecimal calculateInitialPrice(SeatConfiguration seat,
                                                    BigDecimal basePrice) {
        return basePrice.multiply(seat.getPricingMultiplier())
                .setScale(2, RoundingMode.HALF_UP);
    }

    public static class SeatNotAvailableException extends RuntimeException {
        public SeatNotAvailableException(String seatNumber) {
            super("Seat " + seatNumber + " is not available");
        }
    }

    public static class InvalidPriceException extends RuntimeException {
        public InvalidPriceException(String message) {
            super(message);
        }
    }

    public static class InvalidSeatStateException extends RuntimeException {
        public InvalidSeatStateException(String message) {
            super(message);
        }
    }

}
