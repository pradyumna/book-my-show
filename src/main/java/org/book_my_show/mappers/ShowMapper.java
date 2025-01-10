package org.book_my_show.mappers;

import org.book_my_show.domain.movie.Movie;
import org.book_my_show.domain.screen.Screen;
import org.book_my_show.domain.show.Show;
import org.book_my_show.domain.show.ShowSeat;
import org.book_my_show.domain.status.ShowStatus;
import org.book_my_show.domain.theatre.Theatre;
import org.book_my_show.dto.SeatConfigurationDTO;
import org.book_my_show.dto.ShowDTO;
import org.book_my_show.dto.ShowSeatDTO;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public abstract class ShowMapper implements BaseMapper<ShowDTO, Show> {

    @Autowired
    protected ShowSeatMapper showSeatMapper;

    @Override
    @Mapping(target = "movieId", source = "movie.id")
    @Mapping(target = "screenId", source = "screen.id")
    @Mapping(target = "theatreId", source = "theatre.id")
    @Mapping(target = "showSeats", qualifiedByName = "toShowSeatSet")
    public abstract ShowDTO toDto(Show show);

    @Named("toShowList")
    public abstract List<ShowDTO> toDtoList(List<Show> shows);

    @Named("toShowSeatSet")
    public Set<ShowSeatDTO> toShowSeatSet(Set<ShowSeat> seats){
        Set<ShowSeatDTO> seatDTOSet = new HashSet<>();
        if(Objects.nonNull(seats)){
            seats.forEach(seat->{
                ShowSeatDTO seatDTO = new ShowSeatDTO();
                SeatConfigurationDTO configurationDTO = new SeatConfigurationDTO();
                configurationDTO.setSeatId(seat.getSeat().getSeatId());
                configurationDTO.setSeatType(seat.getSeat().getSeatType());
                configurationDTO.setRowId(seat.getSeat().getRowId());
                configurationDTO.setCategory(seat.getSeat().getCategory());
                seatDTO.setSeat(configurationDTO);
                seatDTO.setPrice(seat.getPrice());
                seatDTOSet.add(seatDTO);
            });
        }
        return seatDTOSet;
    }

    public Show createShow(ShowDTO dto, Theatre theatre, Movie movie, Screen screen) {
        if (dto == null) {
            return null;
        }

        return Show.createShow(
                theatre,
                movie,
                screen,
                dto.getShowTime(),
                dto.getBasePrice(),
                movie.getDuration() // Getting duration from movie
        );
    }

    @Named("updateShow")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public void updateShow(@MappingTarget Show show, ShowDTO dto) {
        if (dto == null) {
            return;
        }

        if (dto.getStatus() != null && show.getShowStatus() != dto.getStatus()) {
            if (dto.getStatus() == ShowStatus.CANCELLED) {
                //do something here
            }
        }
    }

    // just to validate show timing conflicts
    public boolean hasTimeConflict(Show existingShow, ShowDTO newShowDto) {
        if (existingShow == null || newShowDto == null) {
            return false;
        }

        LocalDateTime newShowTime = newShowDto.getShowTime();
        Duration movieDuration = existingShow.getDuration(); // Assuming same movie

        LocalDateTime existingEndTime = existingShow.getShowTime().plus(movieDuration);
        LocalDateTime newEndTime = newShowTime.plus(movieDuration);

        return !(existingEndTime.isBefore(newShowTime) ||
                existingShow.getShowTime().isAfter(newEndTime));
    }
}
