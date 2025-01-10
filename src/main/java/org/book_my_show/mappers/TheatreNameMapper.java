package org.book_my_show.mappers;

import org.book_my_show.domain.theatre.TheatreName;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface TheatreNameMapper {

    default String toDto(TheatreName name) {
        return name != null ? name.getTheatreName() : null;
    }

    default TheatreName toDomain(String value) {
        return value != null ? new TheatreName(value) : null;
    }
}
