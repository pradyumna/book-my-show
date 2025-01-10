package org.book_my_show.domain.theatre;

import jakarta.persistence.*;
import org.book_my_show.domain.BaseEntity;
import org.book_my_show.domain.screen.Screen;
import org.book_my_show.domain.show.Show;
import org.book_my_show.domain.embed.Address;
import org.book_my_show.domain.status.TheatreStatus;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "theatres")
public class Theatre extends BaseEntity {
    @Embedded
    private TheatreName name;

    @Embedded
    private LicenseNumber licenseNumber;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "theatre", cascade = CascadeType.ALL)
    private Set<Screen> screens = new HashSet<>();

    @OneToMany(mappedBy = "theatre", cascade = CascadeType.ALL)
    private Set<Show> shows = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private TheatreStatus theatreStatus = TheatreStatus.ACTIVE;

    public Theatre(){}

    public Theatre(TheatreName name ){
        this.name=Objects.requireNonNull(name, "Theatre name cannot be null");
    }

    public Theatre(TheatreName name, LicenseNumber licenseNumber, Address address) {
        this.name = Objects.requireNonNull(name, "Theatre name cannot be null");
        this.licenseNumber = Objects.requireNonNull(licenseNumber, "License number cannot be null");
        this.address = Objects.requireNonNull(address, "Address cannot be null");
        this.theatreStatus = TheatreStatus.INACTIVE;
    }

    public void addScreen(Screen screen) {
        Objects.requireNonNull(screen, "Screen cannot be null");
        validateScreenAddition(screen);
        screens.add(screen);
        screen.assignTheatre(this);
    }

    private void validateScreenAddition(Screen screen) {
        boolean screenNumberExists = screens.stream()
                .anyMatch(existingScreen -> existingScreen.getScreenNumber().equals(screen.getScreenNumber()));

        if (screenNumberExists) {
            throw new Screen.DuplicateScreenException(this.getId(), screen.getScreenNumber());
        }
    }

    public void addShow(Show show) {
        Objects.requireNonNull(show, "Show cannot be null");
        validateShowAddition(show);
        shows.add(show);
        show.assignTheatre(this);
    }

    private void validateShowAddition(Show show) {
        if (this.theatreStatus !=TheatreStatus.ACTIVE) {
            throw new TheatreInactiveException(""+this.getId());
        }

        validateScreenAvailability(show);
        validateShowTimings(show);
    }

    private void validateScreenAvailability(Show newShow) {
        boolean screenExists = screens.stream()
                .anyMatch(screen -> screen.getId().equals(newShow.getScreen().getId()));

        if (!screenExists) {
            throw new Screen.InvalidScreenException("Screen does not belong to this theatre");
        }
    }

    private void validateShowTimings(Show newShow) {
        boolean showTimeConflict = shows.stream()
                .filter(show -> show.getScreen().equals(newShow.getScreen()))
                .anyMatch(show -> show.hasTimeConflict(newShow));

        if (showTimeConflict) {
            throw new Show.ShowTimingConflictException("Show timing conflicts with existing show");
        }
    }

    // Status management
    public void activate() {
        if (screens.isEmpty()) {
            throw new TheatreActivationException("Cannot activate theatre without screens");
        }
        this.theatreStatus = TheatreStatus.ACTIVE;
    }

    public void deactivate(String reason) {
        if (this.theatreStatus == TheatreStatus.ACTIVE) {
            throw new TheatreDeactivationException("Cannot deactivate theatre with active shows");
        }
        this.theatreStatus = TheatreStatus.INACTIVE;
    }

    // Getters - No setters to maintain encapsulation
    public TheatreName getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public LicenseNumber getLicenseNumber() {
        return licenseNumber;
    }

    public Set<Screen> getScreens() {
        return Collections.unmodifiableSet(screens);
    }

    public Set<Show> getShows() {
        return Collections.unmodifiableSet(shows);
    }

    public TheatreStatus getTheatreStatus() {
        return theatreStatus;
    }


    public static class TheatreInactiveException extends RuntimeException{
        public TheatreInactiveException(String message){
            super(message);
        }
    }

    public static class TheatreDeactivationException extends RuntimeException{
        public TheatreDeactivationException(String message){
            super(message);
        }
    }

    public static class TheatreActivationException extends RuntimeException{
        public TheatreActivationException(String message){
            super(message);
        }
    }
}
