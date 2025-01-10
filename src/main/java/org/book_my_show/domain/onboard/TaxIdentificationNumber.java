package org.book_my_show.domain.onboard;

import jakarta.persistence.Embeddable;
import lombok.Value;

@Value
@Embeddable
public class TaxIdentificationNumber {
    String taxId;

    public TaxIdentificationNumber(){this.taxId=null;}

    public TaxIdentificationNumber(String value) {
        if (!isValidFormat(value)) {
            throw new IllegalArgumentException("Invalid registration number format");
        }
        this.taxId = value;
    }

    private boolean isValidFormat(String value) {
        // Add your registration number validation logic
        return true;
    }
}
