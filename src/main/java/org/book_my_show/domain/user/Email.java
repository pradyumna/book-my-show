package org.book_my_show.domain.user;

import jakarta.persistence.Embeddable;
import lombok.Value;

@Value
@Embeddable
public class Email {
    String emailId;

    public Email(){this.emailId=null;}

    public static Email of(String email) {
        return new Email(email);
    }

    public Email(String emailId) {
        if (emailId == null || !isValidEmail(emailId)) {
            throw new IllegalArgumentException("Invalid email format");
        }
        this.emailId = emailId.toLowerCase().trim();
    }

    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
}
