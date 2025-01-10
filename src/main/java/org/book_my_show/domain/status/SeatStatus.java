package org.book_my_show.domain.status;

public enum SeatStatus {
    AVAILABLE("Seat available"),
    BLOCKED("Seat temporarily blocked"),
    BOOKED("Seat booked"),
    UNDER_MAINTENANCE("Seat under maintenance"),
    CANCELLED("Booking cancelled"),
    CONFIRMED("Booking confirmed");

    private final String description;

    SeatStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
