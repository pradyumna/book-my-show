package org.book_my_show.domain.status;

public enum ShowStatus {
    SCHEDULED("Show scheduled"),
    CANCELLED("Show cancelled"),
    IN_PROGRESS("Show in progress"),
    COMPLETED("Show completed"),
    BOOKING_OPEN("Booking open"),
    BOOKING_CLOSED("Booking closed");

    private final String description;

    ShowStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
