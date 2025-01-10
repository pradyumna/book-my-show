package org.book_my_show.dto;

import lombok.*;
import org.book_my_show.domain.status.ScreenStatus;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ScreenDTO extends BaseDTO{
    private Long theatreId;
    private String screenNumber;
    private Integer capacity;
    private Set<SeatConfigurationDTO> seatConfiguration;
    private ScreenStatus status;
}
