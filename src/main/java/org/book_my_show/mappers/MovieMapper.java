package org.book_my_show.mappers;

import org.book_my_show.dto.MovieDTO;
import org.book_my_show.domain.movie.Movie;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import org.mapstruct.*;

import java.util.List;
import java.util.Set;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface MovieMapper extends BaseMapper<MovieDTO, Movie> {

    @Override
    @Mapping(target = "title", source = "title")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "duration", source = "duration")
    @Mapping(target = "language", source = "language")
    @Mapping(target = "genres", source = "genres")
    @Mapping(target = "releaseDate", source = "releaseDate")
    @Mapping(target = "subtitleLanguages", source = "subtitleLanguages")
    @Mapping(target = "status", source = "movieStatus")
    MovieDTO toDto(Movie movie);

    List<MovieDTO> toDtoList(List<Movie> movies);

    @Override
    @Mapping(target = "genres", ignore = true)
    @Mapping(target = "subtitleLanguages", ignore = true)
    Movie toDomain(MovieDTO dto);

    @Named("partialUpdate")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "genres", ignore = true)
    @Mapping(target = "subtitleLanguages", ignore = true)
    void partialUpdate(@MappingTarget Movie entity, MovieDTO dto);

    // Custom methods for collection handling
    default void updateGenres(Movie movie, Set<String> genres) {
        if (genres != null) {
            movie.getGenres().clear();
            genres.forEach(movie::addGenre);
        }
    }

    default void updateSubtitleLanguages(Movie movie, Set<String> languages) {
        if (languages != null) {
            movie.getSubtitleLanguages().clear();
            movie.getSubtitleLanguages().addAll(languages);
        }
    }

    @AfterMapping
    default void handleCollections(@MappingTarget Movie movie, MovieDTO dto) {
        updateGenres(movie, dto.getGenres());
        updateSubtitleLanguages(movie, dto.getSubtitleLanguages());
    }

    // Factory method for creating new Movie instances
    default Movie createNewMovie(MovieDTO dto) {
        Movie movie = new Movie(
                dto.getTitle(),
                dto.getDuration(),
                dto.getLanguage(),
                dto.getReleaseDate()
        );

        if (dto.getDescription() != null) {
            movie.updateMovieDescription(dto.getDescription());
        }

        if (dto.getStatus() != null) {
            movie.updateStatus(dto.getStatus());
        }

        handleCollections(movie, dto);
        return movie;
    }
}