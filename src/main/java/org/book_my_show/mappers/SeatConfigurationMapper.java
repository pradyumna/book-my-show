package org.book_my_show.mappers;

import org.book_my_show.domain.screen.Screen;
import org.book_my_show.domain.screen.SeatConfiguration;
import org.book_my_show.dto.ScreenDTO;
import org.book_my_show.dto.SeatConfigurationDTO;
import org.mapstruct.*;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface SeatConfigurationMapper extends DomainObjectMapper<SeatConfigurationDTO, SeatConfiguration> {

    @Override
    //@Mapping(target = "theatreId", expression = "java(entity.getTheatre().getId())")
    //@Mapping(target = "seatConfiguration", source = "seatConfiguration", qualifiedByName = "toSeatConfigurationDtoSet")
    SeatConfigurationDTO toDto(SeatConfiguration entity);

    @Override
    //@Mapping(target = "theatre", ignore = true)
    //@Mapping(target = "seatConfiguration", source = "seatConfiguration", qualifiedByName = "toSeatConfigurationSet")
    SeatConfiguration toDomain(SeatConfigurationDTO dto);

    @Named("toSeatConfigurationDtoSet")
    default Set<SeatConfigurationDTO> toDtoSet(Set<SeatConfiguration> domains) {
        if (domains == null) {
            return Collections.emptySet();
        }
        return domains.stream()
                .map(this::toDto)
                .collect(Collectors.toSet());
    }

    @Named("toSeatConfigurationSet")
    default Set<SeatConfiguration> toDomainSet(Set<SeatConfigurationDTO> dtos) {
        if (dtos == null) {
            return Collections.emptySet();
        }
        return dtos.stream()
                .map(this::toDomain) // Use the specific toDomain method here.
                .collect(Collectors.toSet());
    }

    @Named("createSeatConfig")
    default SeatConfiguration createSeatConfig(SeatConfigurationDTO dto){
        return SeatConfiguration.create(dto.getRowId(),dto.getSeatId(),dto.getSeatType(),dto.getCategory());
    }
}