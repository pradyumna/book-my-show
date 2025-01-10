package org.book_my_show.mappers;

import org.book_my_show.dto.BookingSeatDTO;
import org.book_my_show.domain.booking.BookingSeat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookingSeatMapper {
    //@Mapping(source = "booking.id", target = "bookingId", ignore = true)
    //@Mapping(source = "showSeat.id", target = "showSeatId", ignore = true)
    BookingSeatDTO toDto(BookingSeat bookingSeat);

    //@Mapping(source = "bookingId", target = "booking.id", ignore = true)
    //@Mapping(source = "showSeatId", target = "showSeat.id", ignore = true)
    BookingSeat toEntity(BookingSeatDTO dto);
}
