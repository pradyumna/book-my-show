package org.book_my_show.domain.onboard;

import jakarta.persistence.Embeddable;
import lombok.Value;

@Value
@Embeddable
public class BusinessName {
    String businessName;

    public BusinessName(){this.businessName=null;}

    public BusinessName(String businessName) {
        if (businessName == null || businessName.trim().isEmpty()) {
            throw new IllegalArgumentException("Business name cannot be empty");
        }
        this.businessName = businessName;
    }
}
