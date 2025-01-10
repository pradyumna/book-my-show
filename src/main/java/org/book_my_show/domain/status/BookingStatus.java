package org.book_my_show.domain.status;

public enum BookingStatus {
    INITIATED("Booking initiated"),
    PAYMENT_PENDING("Payment pending"),
    CONFIRMED("Booking confirmed"),
    CANCELLED("Booking cancelled"),
    COMPLETED("Booking completed"),
    EXPIRED("Booking expired");

    private final String description;

    BookingStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
