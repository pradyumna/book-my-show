package org.book_my_show.domain.status;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public enum SeatType {
    STANDARD(BigDecimal.ONE),
    RECLINER(new BigDecimal("1.5")),
    LOUNGER(new BigDecimal("1.3"));

    private final BigDecimal priceMultiplier;

    SeatType(BigDecimal priceMultiplier) {
        this.priceMultiplier = priceMultiplier;
    }
}
