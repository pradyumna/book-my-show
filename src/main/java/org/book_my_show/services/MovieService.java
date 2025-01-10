package org.book_my_show.services;


import lombok.RequiredArgsConstructor;
import org.book_my_show.dto.MovieDTO;
import org.book_my_show.domain.movie.Movie;
import org.book_my_show.exceptions.ResourceNotFoundException;
import org.book_my_show.mappers.MovieMapper;
import org.book_my_show.repo.MovieRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    public MovieDTO createMovie(MovieDTO request) {
        Movie movie = movieMapper.createNewMovie(request);
        movie = movieRepository.save(movie);
        return movieMapper.toDto(movie);
    }

    public MovieDTO updateMovie(Long id, MovieDTO request) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));

        movieMapper.partialUpdate(movie, request);
        movie = movieRepository.save(movie);

        return movieMapper.toDto(movie);
    }

    public void deleteMovie(Long id){
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));
        movieRepository.deleteById(movie.getId());
    }

}
