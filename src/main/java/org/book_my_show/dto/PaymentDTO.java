package org.book_my_show.dto;

import lombok.*;
import org.book_my_show.domain.status.PaymentMethod;
import org.book_my_show.domain.status.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PaymentDTO extends BaseDTO{
    private Long bookingId;
    private String transactionId;
    private PaymentMethod method;
    private PaymentStatus status;
    private BigDecimal amount;
    private LocalDateTime transactionTime;
    private String paymentGatewayResponse;
}
