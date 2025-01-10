package org.book_my_show.domain.status;

public enum PaymentStatus {
    PENDING("Payment pending"),
    COMPLETED("Completed"),
    PROCESSING("Payment processing"),
    SUCCESS("Payment successful"),
    FAILED("Payment failed"),
    REFUNDED("Payment refunded"),
    PARTIALLY_REFUNDED("Payment partially refunded");

    private final String description;

    PaymentStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
