package org.book_my_show.repo;

import org.book_my_show.domain.movie.Movie;
import org.book_my_show.domain.status.MovieStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie,Long> {
    List<Movie> findByMovieStatus(MovieStatus status);
    List<Movie> findByLanguage(String language);
}
