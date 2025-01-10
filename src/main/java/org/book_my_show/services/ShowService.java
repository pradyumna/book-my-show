package org.book_my_show.services;


import lombok.RequiredArgsConstructor;
import org.book_my_show.domain.movie.Movie;
import org.book_my_show.domain.screen.Screen;
import org.book_my_show.domain.theatre.Theatre;
import org.book_my_show.dto.ShowDTO;
import org.book_my_show.domain.show.Show;
import org.book_my_show.exceptions.ScreenNotFoundException;
import org.book_my_show.exceptions.ShowSlotUnavailableException;
import org.book_my_show.exceptions.TheatreNotFoundException;
import org.book_my_show.mappers.ShowMapper;
import org.book_my_show.repo.MovieRepository;
import org.book_my_show.repo.ScreenRepository;
import org.book_my_show.repo.ShowRepository;
import org.book_my_show.repo.TheatreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.monitor.MonitorSettingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ShowService {
    //Assuming its 3 hours
    private static final long DEFAULT_SHOW_DURATION = 3;

    private final ShowRepository showRepository;
    private final TheatreRepository theatreRepository;
    private final ShowMapper showMapper;
    private final MovieRepository movieRepository;
    private final ScreenRepository screenRepository;

    public List<ShowDTO> getShowsInCity(String city, LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);

        List<Show> shows = showRepository.findShowsInCity(city, startOfDay, endOfDay);
        return shows.stream()
                .map(showMapper::toDto)
                .collect(Collectors.toList());
    }


    public ShowDTO createShow(ShowDTO showDTO){
        LocalDateTime calculatedEndTime = calculateEndTime(showDTO.getShowTime());
        List<Show> shows = showRepository.findOverlappingShows(showDTO.getTheatreId(), showDTO.getShowTime().toLocalDate(),showDTO.getShowTime(),calculatedEndTime);
        if(!shows.isEmpty()){
            throw new ShowSlotUnavailableException("Show slot is already occupied");
        }
        Optional<Theatre> theatre = theatreRepository.findById(showDTO.getTheatreId());
        Optional<Movie> movie = movieRepository.findById(showDTO.getMovieId());
        Optional<Screen> screen = screenRepository.findById(showDTO.getScreenId());
        Show show = showMapper.createShow(showDTO,
                                          theatre.orElseThrow(TheatreNotFoundException::new),
                                          movie.orElseThrow(MonitorSettingException::new),
                                           screen.orElseThrow(ScreenNotFoundException::new));
        show = showRepository.save(show);
        return showMapper.toDto(show);
    }

    private LocalDateTime calculateEndTime(LocalDateTime showTime) {
        return showTime.plusHours(DEFAULT_SHOW_DURATION);
    }
}
