package org.book_my_show.mappers;

import org.book_my_show.domain.show.ShowSeat;
import org.book_my_show.dto.ShowSeatDTO;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public abstract class ShowSeatMapper {

    @Autowired
    protected SeatConfigurationMapper seatConfigurationMapper;

    //@Mapping(target = "id", ignore = true)
    //@Mapping(target = "createdAt", ignore = true)
    //@Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "showId", ignore = true)
    @Mapping(target = "seat", ignore = true)
    public abstract ShowSeatDTO toDto(ShowSeat entity);

    @Named("toShowSeatSet")
    public Set<ShowSeatDTO> toShowSeatDtoSet(Set<ShowSeat> showSeats) {
        if (showSeats == null) {
            return Collections.emptySet();
        }
        return showSeats.stream()
                .map(this::toDto)
                .collect(Collectors.toSet());
    }

    public ShowSeat createShowSeat(ShowSeatDTO dto, BigDecimal basePrice) {
        return ShowSeat.create(
                seatConfigurationMapper.createSeatConfig(dto.getSeat()),
                basePrice
        );
    }
}
