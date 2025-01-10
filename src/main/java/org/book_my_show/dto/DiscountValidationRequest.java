package org.book_my_show.dto;

import lombok.Builder;
import lombok.Data;
import org.book_my_show.domain.status.SeatType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class DiscountValidationRequest {
    private String discountCode;
    private Long userId;
    private Long theatreId;
    private LocalDateTime showTime;
    private BigDecimal ticketPrice;
    private SeatType seatType;
}
