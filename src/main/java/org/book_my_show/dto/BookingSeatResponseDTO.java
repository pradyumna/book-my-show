package org.book_my_show.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.book_my_show.domain.status.SeatStatus;
import org.book_my_show.domain.status.SeatType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingSeatResponseDTO {
    private String showSeatId;
    private String seatNumber;
    private String rowNumber;
    private SeatType seatType;

    // Price details
    private BigDecimal originalPrice;
    private BigDecimal priceCharged;
    private BigDecimal discountApplied;
    private String discountCode;

    // Status
    private SeatStatus status;
    private LocalDateTime cancellationTime;
    private String cancellationReason;
}
