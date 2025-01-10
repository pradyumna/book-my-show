package org.book_my_show.domain.status;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public enum SeatCategory {
    REGULAR(BigDecimal.ONE),
    PREMIUM(new BigDecimal("1.25"));

    private final BigDecimal priceMultiplier;

    SeatCategory(BigDecimal priceMultiplier) {
        this.priceMultiplier = priceMultiplier;
    }
}
