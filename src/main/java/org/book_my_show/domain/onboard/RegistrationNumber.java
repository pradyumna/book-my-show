package org.book_my_show.domain.onboard;

import jakarta.persistence.Embeddable;
import lombok.Value;

@Value
@Embeddable
public class RegistrationNumber {
    String regNum;

    public RegistrationNumber(){this.regNum=null;}

    public RegistrationNumber(String regNum) {
        if (!isValidFormat(regNum)) {
            throw new IllegalArgumentException("Invalid registration number format");
        }
        this.regNum = regNum;
    }

    private boolean isValidFormat(String value) {
        // Add your registration number validation logic
        return true;
    }
}
