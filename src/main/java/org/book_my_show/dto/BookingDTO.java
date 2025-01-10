package org.book_my_show.dto;

import lombok.*;
import org.book_my_show.domain.status.BookingStatus;
import org.book_my_show.domain.status.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BookingDTO extends BaseDTO{
    private String bookingReference;
    private Long userId;
    private Long showId;
    private Set<BookingSeatDTO> seats;
    private BookingStatus status;
    private LocalDateTime bookingTime;
    private BigDecimal totalAmount;
    private PaymentStatus paymentStatus;
}
