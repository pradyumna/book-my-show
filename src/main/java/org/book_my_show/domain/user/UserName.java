package org.book_my_show.domain.user;

import jakarta.persistence.Embeddable;
import lombok.Value;

@Value
@Embeddable
public class UserName {
    String userName;

    public UserName(){this.userName=null;}

    public UserName(String userName) {
        if (userName == null || userName.trim().length() < 2) {
            throw new IllegalArgumentException("Name must be at least 2 characters");
        }
        this.userName = userName.trim();
    }
}
