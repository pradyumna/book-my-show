package org.book_my_show.domain.status;

public enum TheatreStatus {
    ACTIVE("Theatre active"),
    INACTIVE("Theatre inactive"),
    UNDER_MAINTENANCE("Under maintenance"),
    BLACKLISTED("Theatre blacklisted");

    private final String description;

    TheatreStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
