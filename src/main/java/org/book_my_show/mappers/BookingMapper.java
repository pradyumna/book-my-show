package org.book_my_show.mappers;

import org.book_my_show.domain.show.Show;
import org.book_my_show.domain.user.User;
import org.book_my_show.dto.BookingDTO;
import org.book_my_show.domain.booking.Booking;
import org.book_my_show.domain.booking.BookingSeat;
import org.book_my_show.dto.BookingRequestDTO;
import org.book_my_show.dto.BookingResponseDTO;
import org.book_my_show.dto.BookingSeatResponseDTO;
import org.mapstruct.*;

import java.math.BigDecimal;

@Mapper(componentModel = "spring", uses = {BookingSeatMapper.class, ShowMapper.class})
public abstract class BookingMapper {
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "show.id", target = "showId")
    public abstract BookingDTO toDto(Booking booking);

    @Mapping(target = "user", source = "userId", qualifiedByName = "toUser")
    @Mapping(target = "show", source = "showId", qualifiedByName = "toShow")
    public abstract Booking toEntity(BookingDTO dto);

    @AfterMapping
    protected void calculateTotalAmount(@MappingTarget Booking booking) {
        BigDecimal total = booking.getSeats().stream()
                .map(BookingSeat::getPriceCharged)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        booking.updateBookingAmount(total);
    }

    @Named("toUser")
    public User toUser(Long userId) {
        if (userId == null) return null;
        User user = new User();
        user.setId(userId);
        return user;
    }

    @Named("toShow")
    public Show toShow(Long showId) {
        if (showId == null) return null;
        Show show = new Show();
        show.setId(showId);
        return show;
    }

    @Mapping(source = "show", target = "show")
    @Mapping(source = "show.theatre.name.theatreName", target = "theatreName")
    @Mapping(source = "show.movie.title", target = "movieName")
    @Mapping(source = "show.screen.screenNumber", target = "screenName")
    public abstract BookingResponseDTO toResponseDTO(Booking booking);

    @Mapping(target = "showSeatId", source = "showSeat.seat.seatNumber")
    @Mapping(target = "seatNumber", source = "showSeat.seat.seatNumber")
    @Mapping(target = "rowNumber", source = "showSeat.seat.rowId")
    @Mapping(target = "seatType", source = "showSeat.seat.seatType")
    @Mapping(target = "originalPrice", source = "showSeat.price")
    public abstract BookingSeatResponseDTO toSeatResponseDTO(BookingSeat bookingSeat);

    public abstract Booking toEntity(BookingRequestDTO bookingRequestDTO);
}
