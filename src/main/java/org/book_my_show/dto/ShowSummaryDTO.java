package org.book_my_show.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.book_my_show.domain.status.ShowStatus;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShowSummaryDTO {
    private Long showId;
    private String movieName;
    private String theatreName;
    private String screenName;
    private LocalDateTime showTime;
    private ShowStatus status;
}
