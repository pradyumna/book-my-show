package org.book_my_show.domain.screen;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.book_my_show.domain.BaseEntity;
import org.book_my_show.domain.status.ScreenStatus;
import org.book_my_show.domain.status.SeatCategory;
import org.book_my_show.domain.status.SeatType;
import org.book_my_show.domain.theatre.Theatre;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "screens")
@Getter
public class Screen extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theatre_id", nullable = false)
    @JsonIgnore
    private Theatre theatre;

    @NotBlank
    @Column(nullable = false)
    private String screenNumber;

    @Min(1)
    private Integer capacity;


    @ElementCollection
    @CollectionTable(name = "seat_config", joinColumns = @JoinColumn(name = "seat_config_id"))
    private Set<SeatConfiguration> seatConfiguration = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private ScreenStatus screenStatus = ScreenStatus.ACTIVE;

    public Screen(){}

    private Screen(Theatre theatre, String screenNumber, Integer capacity) {
        this.theatre = Objects.requireNonNull(theatre, "Theatre cannot be null");
        this.screenNumber = validateScreenNumber(screenNumber);
        this.capacity = validateCapacity(capacity);
        this.screenStatus = ScreenStatus.ACTIVE;
        this.seatConfiguration = new HashSet<>();
    }

    public static Screen createScreen(Theatre theatre, String screenNumber, Integer capacity) {
        return new Screen(theatre, screenNumber, capacity);
    }

    public Set<SeatConfiguration> getSeatConfig(){return this.seatConfiguration;}

    public void assignTheatre(Theatre theatre){
        this.theatre=theatre;
    }

    public String getScreenNumber(){return this.screenNumber;}
    public ScreenStatus getScreenStatus(){return this.screenStatus;}

    public void addSeatConfiguration(String rowId, Integer seatNumber,
                                     SeatType seatType, SeatCategory category) {
        validateScreenStatus();

        SeatConfiguration newSeat = SeatConfiguration.create(
                rowId, seatNumber, seatType, category
        );

        validateSeatAddition(newSeat);
        seatConfiguration.add(newSeat);
        this.capacity = seatConfiguration.size();
    }

    public void removeSeatConfiguration(String seatNumber) {
        validateScreenStatus();

        seatConfiguration.removeIf(seat ->
                seat.getSeatNumber().equals(seatNumber));

        // Update capacity
        this.capacity = seatConfiguration.size();
    }

    public List<SeatConfiguration> getAvailableSeatsByCategory(SeatCategory category) {
        return seatConfiguration.stream()
                .filter(SeatConfiguration::isActive)
                .filter(seat -> seat.getCategory() == category)
                .sorted(Comparator.comparing(SeatConfiguration::getSeatNumber))
                .collect(Collectors.toUnmodifiableList());
    }

    public List<SeatConfiguration> getPremiumSeats() {
        return seatConfiguration.stream()
                .filter(SeatConfiguration::isActive)
                .filter(SeatConfiguration::isPremiumSeat)
                .collect(Collectors.toUnmodifiableList());
    }

    public boolean canHostShows() {
        return screenStatus == ScreenStatus.ACTIVE &&
                !seatConfiguration.isEmpty();
    }

    public void deactivateScreen(String reason) {
        if (screenStatus == ScreenStatus.INACTIVE) {
            throw new ScreenOperationException("Screen is already inactive");
        }
        this.screenStatus = ScreenStatus.INACTIVE;
    }

    public Map<SeatType, Long> getCapacityBySeatType() {
        return seatConfiguration.stream()
                .filter(SeatConfiguration::isActive)
                .collect(Collectors.groupingBy(
                        SeatConfiguration::getSeatType,
                        Collectors.counting()
                ));
    }

    private static String validateScreenNumber(String screenNumber) {
        if (screenNumber == null || screenNumber.trim().isEmpty()) {
            throw new InvalidScreenException("Screen number cannot be empty");
        }
        return screenNumber.trim();
    }

    private static Integer validateCapacity(Integer capacity) {
        if (capacity == null || capacity < 1) {
            throw new InvalidScreenException("Capacity must be greater than 0");
        }
        return capacity;
    }

    private void validateScreenStatus() {
        if (screenStatus != ScreenStatus.ACTIVE) {
            throw new ScreenOperationException("Screen is not active");
        }
    }

    private void validateSeatAddition(SeatConfiguration newSeat) {
        boolean seatExists = seatConfiguration.stream()
                .anyMatch(seat -> seat.getSeatNumber()
                        .equals(newSeat.getSeatNumber()));

        if (seatExists) {
            throw new DuplicateSeatException(
                    "Seat " + newSeat.getSeatNumber() + " already exists");
        }
    }

    // Custom exceptions
    public static class InvalidScreenException extends RuntimeException {
        public InvalidScreenException(String message) {
            super(message);
        }
    }

    public static class ScreenOperationException extends RuntimeException {
        public ScreenOperationException(String message) {
            super(message);
        }
    }

    public static class DuplicateSeatException extends RuntimeException {
        public DuplicateSeatException(String message) {
            super(message);
        }
    }

    public static class DuplicateScreenException extends RuntimeException {

        public DuplicateScreenException(Long id, String message) {
            super("Duplicate screen, id:["+id+"], message:["+message+"]");
        }
    }
}
