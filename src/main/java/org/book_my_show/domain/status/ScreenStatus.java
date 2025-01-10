package org.book_my_show.domain.status;

public enum ScreenStatus {
    ACTIVE("Screen active"),
    INACTIVE("Screen inactive"),
    UNDER_MAINTENANCE("Screen under maintenance");

    private final String description;

    ScreenStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
