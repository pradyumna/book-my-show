package org.book_my_show.mappers;

import org.book_my_show.domain.screen.Screen;
import org.book_my_show.domain.theatre.Theatre;
import org.book_my_show.dto.ScreenDTO;
import org.book_my_show.domain.status.ScreenStatus;
import org.mapstruct.*;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        uses = {SeatConfigurationMapper.class},
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface ScreenMapper extends BaseMapper<ScreenDTO, Screen> {

    @Override
    @Mapping(target = "theatreId", expression = "java(entity.getTheatre().getId())")
    @Mapping(target = "seatConfiguration", source = "seatConfiguration")
    ScreenDTO toDto(Screen entity);

    @Override
    @Mappings({
            @Mapping(target = "theatre", ignore = true),
            @Mapping(target = "seatConfiguration", source = "seatConfiguration", qualifiedByName = "toSeatConfigurationSet")
    })
    Screen toDomain(ScreenDTO dto);


    @Named("toScreenSet")
    default Set<ScreenDTO> toScreenDtoSet(Set<Screen> screens) {
        if (screens == null) {
            return Collections.emptySet();
        }
        return screens.stream()
                .map(this::toDto)
                .collect(Collectors.toSet());
    }

    // Factory method for creating new Screen remains the same
    default Screen createScreen(ScreenDTO dto, Theatre theatre) {
        Screen screen = Screen.createScreen(
                theatre,
                dto.getScreenNumber(),
                dto.getCapacity()
        );

        if (dto.getSeatConfiguration() != null) {
            dto.getSeatConfiguration().forEach(seatDto ->
                    screen.addSeatConfiguration(
                            seatDto.getRowId(),
                            seatDto.getSeatId(),
                            seatDto.getSeatType(),
                            seatDto.getCategory()
                    )
            );
        }

        return screen;
    }

    @Named("updateScreen")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    default void updateScreen(@MappingTarget Screen screen, ScreenDTO dto) {
        if (dto.getStatus() != null && screen.getScreenStatus() != dto.getStatus()) {
            if (dto.getStatus() == ScreenStatus.INACTIVE) {
                screen.deactivateScreen("Status updated through DTO");
            }
        }

        if (dto.getSeatConfiguration() != null) {
            dto.getSeatConfiguration().forEach(seatDto -> {
                screen.addSeatConfiguration(
                        seatDto.getRowId(),
                        seatDto.getSeatId(),
                        seatDto.getSeatType(),
                        seatDto.getCategory()
                );
            });
        }
    }



    /*@Override
    @Mapping(target = "theatreId", expression = "java(entity.getTheatre().getId())")
    @Mapping(target = "seatConfiguration", qualifiedByName = "toDtoSet")
    ScreenDTO toDto(Screen entity);

    @Named("toScreenSet")
    default Set<ScreenDTO> toScreenDtoSet(Set<Screen> screens) {
        if (screens == null) {
            return Collections.emptySet();
        }
        return screens.stream()
                .map(this::toDto)
                .collect(Collectors.toSet());
    }

    @Override
    @Mapping(target = "theatre", ignore = true)
    @Mapping(target = "seatConfiguration", source = "seatConfiguration", qualifiedByName = "toDomainSet")
    Screen toEntity(ScreenDTO dto);

    @Named("updateScreen")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    default void updateScreen(@MappingTarget Screen screen, ScreenDTO dto) {
        if (dto.getStatus() != null && screen.getStatus() != dto.getStatus()) {
            if (dto.getStatus() == ScreenStatus.INACTIVE) {
                screen.deactivateScreen("Status updated through DTO");
            }
        }

        if (dto.getSeatConfiguration() != null) {
            dto.getSeatConfiguration().forEach(seatDto -> {
                screen.addSeatConfiguration(
                        seatDto.getRowId(),
                        seatDto.getSeatId(),
                        seatDto.getSeatType(),
                        seatDto.getCategory()
                );
            });
        }
    }

    // Factory method for creating new Screen
    default Screen createScreen(ScreenDTO dto, Theatre theatre) {
        Screen screen = Screen.createScreen(
                theatre,
                dto.getScreenNumber(),
                dto.getCapacity()
        );

        if (dto.getSeatConfiguration() != null) {
            dto.getSeatConfiguration().forEach(seatDto ->
                    screen.addSeatConfiguration(
                            seatDto.getRowId(),
                            seatDto.getSeatId(),
                            seatDto.getSeatType(),
                            seatDto.getCategory()
                    )
            );
        }

        return screen;
    }*/
}
