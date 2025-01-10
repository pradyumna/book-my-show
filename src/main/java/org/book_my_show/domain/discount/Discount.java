package org.book_my_show.domain.discount;

import jakarta.persistence.*;
import lombok.Getter;
import org.book_my_show.domain.BaseEntity;
import org.book_my_show.domain.status.DiscountType;
import org.book_my_show.domain.status.SeatType;
import org.book_my_show.domain.status.ShowTimeSlot;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "discounts")
@Getter
public class Discount extends BaseEntity {

    private String code;

    private String description;

    @Enumerated(EnumType.STRING)
    private DiscountType type;

    @Column(name = "discount_value", precision = 10, scale = 2)
    private BigDecimal discountValue;

    @ElementCollection
    private Set<Long> applicableCityIds;

    @ElementCollection
    private Set<Long> applicableTheatreIds;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<ShowTimeSlot> applicableTimeSlots;

    @ElementCollection
    private Set<SeatType> applicableSeatTypes;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<DayOfWeek> applicableDays;

    private LocalDateTime validFrom;
    private LocalDateTime validUntil;

    private BigDecimal minimumTicketPrice;
    private BigDecimal maximumDiscountAmount;

    private Integer maxUsagePerUser;
    private Integer maxTotalUsage;

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(validUntil);
    }

    public boolean isApplicable(SeatType seatType) {
        return applicableSeatTypes.contains(seatType);
    }
}
