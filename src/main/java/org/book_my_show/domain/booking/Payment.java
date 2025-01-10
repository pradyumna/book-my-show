package org.book_my_show.domain.booking;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.book_my_show.domain.status.PaymentMethod;
import org.book_my_show.domain.status.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Embeddable
@Getter
@AllArgsConstructor
public class Payment {
    private String transactionId;
    private PaymentMethod method;
    private PaymentStatus paymentStatus;
    private BigDecimal amount;
    private LocalDateTime transactionTime;
    private String paymentGatewayResponse;

    public static Payment createPayment(){
        return new Payment("txn:12345", PaymentMethod.UPI,PaymentStatus.COMPLETED, BigDecimal.valueOf(350.0), LocalDateTime.now(),"OK");
    }
}
