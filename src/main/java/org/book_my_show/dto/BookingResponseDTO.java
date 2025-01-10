package org.book_my_show.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.book_my_show.domain.status.BookingStatus;
import org.book_my_show.domain.status.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponseDTO extends BaseDTO {
    private String bookingReference;
    private Long userId;
    private Long showId;

    private ShowSummaryDTO show;

    private Set<BookingSeatResponseDTO> seats;
    private BookingStatus status;
    private LocalDateTime bookingTime;
    private BigDecimal totalAmount;
    private PaymentStatus paymentStatus;

    private LocalDateTime showTime;
    private String theatreName;
    private String movieName;
    private String screenName;
}
