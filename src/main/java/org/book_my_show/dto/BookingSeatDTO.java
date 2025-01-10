package org.book_my_show.dto;

import lombok.*;
import org.book_my_show.domain.status.SeatStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BookingSeatDTO extends BaseDTO{
    private Long bookingId;
    private String showSeatId;
    private BigDecimal priceCharged;
    private BigDecimal discountApplied;
    private String discountCode;
    private SeatStatus status;
    private LocalDateTime cancellationTime;
    private String cancellationReason;
}
