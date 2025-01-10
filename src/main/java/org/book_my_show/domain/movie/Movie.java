package org.book_my_show.domain.movie;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.book_my_show.domain.BaseEntity;
import org.book_my_show.domain.status.MovieStatus;

import java.time.Duration;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "movies")
@Getter
public class Movie extends BaseEntity {
    @NotBlank
    @Column(nullable = false)
    private String title;

    private String description;

    @NotNull
    private Duration duration;

    @NotBlank
    private String language;

    @ElementCollection
    @CollectionTable(name = "movie_genres")
    private Set<String> genres = new HashSet<>();

    @NotNull
    private LocalDate releaseDate;

    @ElementCollection
    @CollectionTable(name = "movie_subtitle_languages")
    private Set<String> subtitleLanguages = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private MovieStatus movieStatus;

    public Movie(){}

    public Movie(String title, Duration duration, String language, LocalDate releaseDate) {
        this.title = title;
        this.duration = duration;
        this.language = language;
        this.releaseDate = releaseDate;
        this.movieStatus = MovieStatus.UPCOMING;
    }

    public void addGenre(String genre) {
        genres.add(genre);
    }

    public void removeGenre(String genre) {
        genres.remove(genre);
    }

    public void updateStatus(MovieStatus status) {
        this.movieStatus = status;
    }

    public void updateMovieDescription(String description){
        this.description=description;
    }
}
