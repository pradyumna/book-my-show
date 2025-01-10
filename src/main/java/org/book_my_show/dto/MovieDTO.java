package org.book_my_show.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.book_my_show.domain.status.MovieStatus;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MovieDTO extends BaseDTO{
    private String title;
    private String description;
    private Duration duration;
    private String language;
    private Set<String> genres;
    @JsonFormat(pattern = "d/MM/yyyy")
    private LocalDate releaseDate;
    private Set<String> subtitleLanguages;
    private MovieStatus status;
}
