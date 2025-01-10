package org.book_my_show.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.book_my_show.domain.status.SeatStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ShowSeatDTO extends BaseDTO{
    private Long showId;
    private SeatConfigurationDTO seat;
    //private SeatStatus status;
    private BigDecimal price;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime showTime;
}
