package org.book_my_show.domain.status;

public enum UserType {
    CUSTOMER("Regular customer"),
    THEATRE_ADMIN("Theatre administrator"),
    PLATFORM_ADMIN("Platform administrator"),
    GUEST("Guest user");

    private final String description;

    UserType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
