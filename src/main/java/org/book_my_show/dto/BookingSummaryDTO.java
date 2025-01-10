package org.book_my_show.dto;

import lombok.Builder;
import lombok.Data;
import org.book_my_show.domain.status.BookingStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class BookingSummaryDTO {
    private BookingStatus status;
    private LocalDateTime bookingTime;
    private BigDecimal totalOriginalAmount;
    private BigDecimal totalDiscountAmount;
    private BigDecimal finalAmount;
}
