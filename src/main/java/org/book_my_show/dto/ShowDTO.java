package org.book_my_show.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.book_my_show.domain.status.ShowStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ShowDTO extends BaseDTO{
    private Long theatreId;
    private Long movieId;
    private Long screenId;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime showTime;
    private BigDecimal basePrice;
    private Set<ShowSeatDTO> showSeats;
    private ShowStatus status;
}
