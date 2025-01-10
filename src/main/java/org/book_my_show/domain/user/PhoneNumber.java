package org.book_my_show.domain.user;

import jakarta.persistence.Embeddable;
import lombok.Value;
import org.apache.logging.log4j.util.Strings;

@Value
@Embeddable
public class PhoneNumber {
    String phoneNumber;

    public PhoneNumber(){this.phoneNumber=null;}

    public PhoneNumber(String phoneNumber){
        if (!Strings.isEmpty(phoneNumber) && isValidPhone(phoneNumber)) {
            this.phoneNumber = phoneNumber.trim();
        } else {
            throw new IllegalArgumentException("Invalid phone number format");
        }
    }

    private boolean isValidPhone(String phone) {
        return phone.matches("^\\+?[1-9]\\d{9,14}$");
    }

}
