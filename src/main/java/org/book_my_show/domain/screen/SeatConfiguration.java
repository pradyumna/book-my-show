package org.book_my_show.domain.screen;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.book_my_show.domain.BaseEntity;
import org.book_my_show.domain.status.SeatCategory;
import org.book_my_show.domain.status.SeatType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@Embeddable
@Getter
public class SeatConfiguration {
    private String rowId;
    private Integer seatNumber;

    @Enumerated(EnumType.STRING)
    private SeatType seatType;

    @Enumerated(EnumType.STRING)
    private SeatCategory category;

    private Boolean isActive = true;

    public SeatConfiguration() {}

    public static SeatConfiguration create(String rowId, Integer seatNumber,
                                           SeatType seatType, SeatCategory category) {
        return new SeatConfiguration(
                validateRowId(rowId),
                validateSeatNumber(seatNumber),
                Objects.requireNonNull(seatType, "Seat type cannot be null"),
                Objects.requireNonNull(category, "Seat category cannot be null"),
                true
        );
    }

    public boolean isActive(){return isActive;}
    public SeatType getSeatType(){return seatType;}
    public SeatCategory getCategory(){return category;}
    public String getRowId(){return rowId;}

    private SeatConfiguration(String rowId, Integer seatNumber,
                              SeatType seatType, SeatCategory category,
                              Boolean isActive) {
        this.rowId = rowId;
        this.seatNumber = seatNumber;
        this.seatType = seatType;
        this.category = category;
        this.isActive = isActive;
    }

    public Integer getSeatId(){return this.seatNumber;}
    public String getSeatNumber() {
        return rowId + seatNumber;
    }

    public BigDecimal getPricingMultiplier() {
        BigDecimal categoryMultiplier = category.getPriceMultiplier();
        BigDecimal typeMultiplier = seatType.getPriceMultiplier();
        return categoryMultiplier.multiply(typeMultiplier)
                .setScale(2, RoundingMode.HALF_UP);
    }

    public SeatConfiguration deactivate() {
        if (!isActive) {
            throw new SeatOperationException("Seat is already deactivated");
        }
        return new SeatConfiguration(rowId, seatNumber, seatType, category, false);
    }

    public SeatConfiguration activate() {
        if (isActive) {
            throw new SeatOperationException("Seat is already active");
        }
        return new SeatConfiguration(rowId, seatNumber, seatType, category, true);
    }

    public boolean isPremiumSeat() {
        return category == SeatCategory.PREMIUM ||
                seatType == SeatType.RECLINER;
    }

    private static String validateRowId(String rowId) {
        if (rowId == null || !rowId.matches("[A-Z]")) {
            throw new InvalidSeatConfigurationException(
                    "Row ID must be a single uppercase letter");
        }
        return rowId;
    }

    private static Integer validateSeatNumber(Integer seatNumber) {
        if (seatNumber == null || seatNumber < 1 || seatNumber > 99) {
            throw new InvalidSeatConfigurationException(
                    "Seat number must be between 1 and 99");
        }
        return seatNumber;
    }

    public static class InvalidSeatConfigurationException extends RuntimeException {
        public InvalidSeatConfigurationException(String message) {
            super(message);
        }
    }

    public static class SeatOperationException extends RuntimeException {
        public SeatOperationException(String message) {
            super(message);
        }
    }
}
