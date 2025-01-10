package org.book_my_show.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ShowDetailsDTO {
    private Long showId;
    private String movieName;
    private String theatreName;
    private String screenName;
    private LocalDateTime showTime;
}
