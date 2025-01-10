package org.book_my_show.dto;

import lombok.*;
import org.book_my_show.domain.status.SeatCategory;
import org.book_my_show.domain.status.SeatType;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SeatConfigurationDTO extends BaseDTO{
    private String rowId;
    private Integer seatId;
    private SeatType seatType;
    private SeatCategory category;
    private Boolean isActive;
    private String seatNumber;
}
