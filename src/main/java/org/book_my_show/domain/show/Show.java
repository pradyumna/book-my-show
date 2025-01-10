package org.book_my_show.domain.show;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.book_my_show.domain.BaseEntity;
import org.book_my_show.domain.movie.Movie;
import org.book_my_show.domain.screen.Screen;
import org.book_my_show.domain.theatre.Theatre;
import org.book_my_show.domain.status.ShowStatus;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "shows")
@Getter
public class Show extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "theatre_id")
    private Theatre theatre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "screen_id", nullable = false)
    private Screen screen;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime showTime;

    @NotNull
    @Column(precision = 10, scale = 2)
    private BigDecimal basePrice;

    @ElementCollection
    @CollectionTable(name = "show_seats", joinColumns = @JoinColumn(name = "show_id"))
    private Set<ShowSeat> showSeats = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private ShowStatus showStatus = ShowStatus.SCHEDULED;

    @NotNull

    private Duration duration;

    public Show(){}

    public static Show createShow(Theatre theatre, Movie movie, Screen screen,
                                  LocalDateTime showTime, BigDecimal basePrice, Duration duration) {
        Show show = new Show();
        show.theatre = Objects.requireNonNull(theatre, "Theatre cannot be null");
        show.movie = Objects.requireNonNull(movie, "Movie cannot be null");
        show.screen = Objects.requireNonNull(screen, "Screen cannot be null");
        show.showTime = Objects.requireNonNull(showTime, "Show time cannot be null");
        show.basePrice = Objects.requireNonNull(basePrice, "Base price cannot be null");
        show.duration = Objects.requireNonNull(duration, "Duration cannot be null");
        show.initializeSeats();
        return show;
    }

    private void initializeSeats() {
        this.showSeats = screen.getSeatConfig().stream()
                .map(seatConfig -> ShowSeat.create(seatConfig, basePrice))
                .collect(Collectors.toSet());
    }

    public boolean hasTimeConflict(Show other) {
        LocalDateTime thisEndTime = this.showTime.plus(this.duration);
        LocalDateTime otherEndTime = other.showTime.plus(other.duration);

        if (!this.screen.equals(other.screen)) {
            return false;
        }

        // Check for time overlap
        return !(thisEndTime.isBefore(other.showTime) ||
                this.showTime.isAfter(otherEndTime));
    }

    public ShowSeat bookSeat(String seatNumber, BigDecimal amount) {
        validateShowStatus();

        ShowSeat existingSeat = findSeat(seatNumber)
                .orElseThrow(() -> new SeatNotFoundException(seatNumber));

        ShowSeat bookedSeat = existingSeat.book(amount);
        showSeats.remove(existingSeat);
        showSeats.add(bookedSeat);

        return bookedSeat;
    }

    public Screen getScreen(){return this.screen;}

    public ShowSeat lockSeat(String seatNumber) {
        validateShowStatus();

        ShowSeat existingSeat = findSeat(seatNumber)
                .orElseThrow(() -> new SeatNotFoundException(seatNumber));

        ShowSeat lockedSeat = existingSeat.lock(Duration.ofMinutes(10));
        showSeats.remove(existingSeat);
        showSeats.add(lockedSeat);

        return lockedSeat;
    }

    public Optional<ShowSeat> findSeat(String seatNumber) {
        return showSeats.stream()
                .filter(seat -> seat.getSeat().getSeatNumber().equals(seatNumber))
                .findFirst();
    }

    public List<ShowSeat> getAvailableSeats() {
        return showSeats.stream()
                .filter(ShowSeat::isAvailable)
                .sorted(Comparator.comparing(seat -> seat.getSeat().getSeatNumber()))
                .collect(Collectors.toUnmodifiableList());
    }

    private void validateShowStatus() {
        if (showStatus != ShowStatus.SCHEDULED) {
            throw new ShowOperationException("Show is not in SCHEDULED state");
        }
        if (showTime.isBefore(LocalDateTime.now())) {
            throw new ShowOperationException("Show time has passed");
        }
    }

    public void assignTheatre(Theatre theatre){
        this.theatre=theatre;
    }

    public static class SeatNotFoundException extends RuntimeException {
        public SeatNotFoundException(String seatNumber) {
            super("Seat " + seatNumber + " not found");
        }
    }

    public static class ShowOperationException extends RuntimeException {
        public ShowOperationException(String message) {
            super(message);
        }
    }

    public static class ShowTimingConflictException extends RuntimeException {
        public ShowTimingConflictException(String message) {
            super(message);
        }
    }
}
