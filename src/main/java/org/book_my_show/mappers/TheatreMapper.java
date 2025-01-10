package org.book_my_show.mappers;

import org.book_my_show.domain.screen.Screen;
import org.book_my_show.domain.screen.SeatConfiguration;
import org.book_my_show.domain.status.TheatreStatus;
import org.book_my_show.domain.theatre.LicenseNumber;
import org.book_my_show.domain.theatre.Theatre;
import org.book_my_show.domain.theatre.TheatreName;
import org.book_my_show.dto.ScreenDTO;
import org.book_my_show.dto.SeatConfigurationDTO;
import org.book_my_show.dto.TheatreDTO;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public abstract class TheatreMapper implements BaseMapper<TheatreDTO, Theatre> {

    @Autowired
    protected ScreenMapper screenMapper;

    @Autowired
    protected AddressMapper addressMapper;

    @Override
    @Mapping(target = "name", expression = "java(mapTheatreName(theatre))")
    @Mapping(target = "licenseNumber", expression = "java(mapLicenseNumber(theatre))")
    @Mapping(target = "address", expression = "java(addressMapper.toDto(theatre.getAddress()))")
    @Mapping(target = "screens", expression = "java(screenMapper.toScreenDtoSet(theatre.getScreens()))")
    @Mapping(target = "theatreId", expression = "java(theatre.getId())")
    public abstract TheatreDTO toDto(Theatre theatre);

    protected String mapTheatreName(Theatre theatre) {
        TheatreName theatreName = theatre.getName();
        return theatreName != null ? theatreName.getTheatreName() : null;
    }

    protected String mapLicenseNumber(Theatre theatre) {
        LicenseNumber licenseNumber = theatre.getLicenseNumber();
        return licenseNumber != null ? licenseNumber.getLicNum() : null;
    }

    public Theatre createTheatre(TheatreDTO dto) {
        if (dto == null) {
            return null;
        }

        Theatre theatre = new Theatre(
                new TheatreName(dto.getName()),
                new LicenseNumber(dto.getLicenseNumber()),
                addressMapper.toDomain(dto.getAddress())
        );

        if (dto.getScreens() != null) {
            dto.getScreens().forEach(screenDto -> {
                Screen screen = screenMapper.createScreen(screenDto, theatre);
                theatre.addScreen(screen);
            });
        }

        if (dto.getStatus() == TheatreStatus.ACTIVE) {
            theatre.activate();
        }

        return theatre;
    }

    @Named("toSeatConfiguration")
    protected SeatConfiguration toSeatConfiguration(SeatConfigurationDTO dto) {
        if (dto == null) return null;
        return SeatConfiguration.create(dto.getRowId(), dto.getSeatId(), dto.getSeatType(), dto.getCategory());
    }

    // Add mapping method for Set<SeatConfiguration>
    @Named("toSeatConfigurationSet")
    protected Set<SeatConfiguration> toSeatConfigurationSet(Set<SeatConfigurationDTO> dtos) {
        if (dtos == null) return new HashSet<>();
        return dtos.stream()
                .map(this::toSeatConfiguration)
                .collect(Collectors.toSet());
    }

    @Override
    @Mapping(target = "screens", source = "screens", qualifiedByName = "toScreenSet")
    public abstract Theatre toDomain(TheatreDTO dto);

    @Named("toScreenSet")
    protected Set<Screen> toScreenSet(Set<ScreenDTO> dtos) {
        if (dtos == null) return new HashSet<>();
        return dtos.stream()
                .map(screenDTO -> Screen.createScreen(null,screenDTO.getScreenNumber(),screenDTO.getCapacity()))
                .collect(Collectors.toSet());
    }

    @Named("toScreenDTOSet")
    protected Set<ScreenDTO> toScreenDTOSet(Set<Screen> screens) {
        if (screens == null) return new HashSet<>();
        return screens.stream()
                .map(screen -> {
                    ScreenDTO dto = new ScreenDTO();
                    dto.setId(screen.getId());
                    // Map other ScreenDTO properties

                    // Map SeatConfiguration using separate method
                    dto.setSeatConfiguration(toSeatConfigurationDTOSet(screen.getSeatConfiguration()));
                    return dto;
                })
                .collect(Collectors.toSet());
    }

    @Named("toSeatConfigurationDTO")
    protected SeatConfigurationDTO toSeatConfigurationDTO(SeatConfiguration config) {
        if (config == null) return null;

        SeatConfigurationDTO dto = new SeatConfigurationDTO();
        dto.setCategory(config.getCategory());
        dto.setSeatId(config.getSeatId());
        dto.setSeatType(config.getSeatType());
        dto.setRowId(config.getRowId());
        dto.setSeatNumber(config.getSeatNumber());
        return dto;
    }

    @Named("toSeatConfigurationDTOSet")
    protected Set<SeatConfigurationDTO> toSeatConfigurationDTOSet(Set<SeatConfiguration> configs) {
        if (configs == null) return new HashSet<>();
        return configs.stream()
                .map(this::toSeatConfigurationDTO)
                .collect(Collectors.toSet());
    }

    @Named("updateTheatre")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public void updateTheatre(@MappingTarget Theatre theatre, TheatreDTO dto) {
        if (dto == null) {
            return;
        }

        if (dto.getStatus() != null && theatre.getTheatreStatus() != dto.getStatus()) {
            if (dto.getStatus() == TheatreStatus.ACTIVE) {
                theatre.activate();
            } else {
                theatre.deactivate("Status updated through DTO");
            }
        }

        if (dto.getScreens() != null) {
            dto.getScreens().forEach(screenDto -> {
                theatre.getScreens().stream()
                        .filter(screen -> screen.getId().equals(screenDto.getId()))
                        .findFirst()
                        .ifPresent(screen -> screenMapper.updateScreen(screen, screenDto));
            });
        }
    }
}
