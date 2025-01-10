package org.book_my_show.domain.theatre;

import jakarta.persistence.Embeddable;
import lombok.Value;

@Value
@Embeddable
public class LicenseNumber {
    String licNum;

    public LicenseNumber(){this.licNum=null;}

    public LicenseNumber(String licNum) {
        if (licNum == null || !licNum.matches("^[A-Z]{2}\\d{6}$")) {
            throw new IllegalArgumentException("Invalid license number format");
        }
        this.licNum = licNum;
    }
}
