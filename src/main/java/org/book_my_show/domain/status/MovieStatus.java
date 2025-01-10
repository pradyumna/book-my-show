package org.book_my_show.domain.status;

public enum MovieStatus {
    UPCOMING("Movie upcoming"),
    NOW_SHOWING("Now showing"),
    END_OF_SHOWING("End of showing"),
    CANCELLED("Movie cancelled");

    private final String description;

    MovieStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
