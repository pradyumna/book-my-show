package org.book_my_show.domain.booking;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import org.book_my_show.domain.show.ShowSeat;
import org.book_my_show.domain.status.SeatStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;


@Embeddable
@Getter
public class BookingSeat {
    private ShowSeat showSeat;
    private BigDecimal priceCharged;
    private BigDecimal discountApplied;
    private String discountCode;
    private SeatStatus seatStatus;
    private LocalDateTime cancellationTime;
    private String cancellationReason;

    public BookingSeat() {
        this.showSeat = null;
        this.priceCharged = null;
        this.discountApplied = null;
        this.discountCode = null;
        this.seatStatus = null;
        this.cancellationTime = null;
        this.cancellationReason = null;
    }

    public static BookingSeat create(ShowSeat showSeat, BigDecimal price,
                                     BigDecimal discount, String discountCode) {
        return new BookingSeat(
                Objects.requireNonNull(showSeat, "ShowSeat cannot be null"),
                Objects.requireNonNull(price, "Price cannot be null"),
                discount,
                discountCode,
                SeatStatus.CONFIRMED,
                null,
                null
        );
    }

    // Constructor with validation
    private BookingSeat(ShowSeat showSeat, BigDecimal priceCharged,
                        BigDecimal discountApplied, String discountCode,
                        SeatStatus seatStatus, LocalDateTime cancellationTime,
                        String cancellationReason) {
        this.showSeat = showSeat;
        this.priceCharged = priceCharged;
        this.discountApplied = discountApplied;
        this.discountCode = discountCode;
        this.seatStatus = seatStatus;
        this.cancellationTime = cancellationTime;
        this.cancellationReason = cancellationReason;
    }

    // Business methods
    public BookingSeat cancel(String reason) {
        return new BookingSeat(
                this.showSeat,
                this.priceCharged,
                this.discountApplied,
                this.discountCode,
                SeatStatus.CANCELLED,
                LocalDateTime.now(),
                reason
        );
    }
}
