package org.book_my_show.domain.user;

import jakarta.persistence.*;
import lombok.Getter;
import org.book_my_show.domain.BaseEntity;
import org.book_my_show.domain.booking.Booking;
import org.book_my_show.domain.status.UserStatus;
import org.book_my_show.domain.status.UserType;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
public class User extends BaseEntity {
    @Embedded
    private Email email;

    @Embedded
    private PhoneNumber phone;

    @Embedded
    private UserName name;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private UserType type=UserType.CUSTOMER;

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus = UserStatus.PENDING_ACTIVATION;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Booking> bookings = new HashSet<>();

    private String profileImageUrl;

    @Column(name = "email_verified")
    private boolean emailVerified = false;

    @Column(name = "phone_verified")
    private boolean phoneVerified = false;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserDevice> devices = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "user_preferences")
    private Set<String> preferences = new HashSet<>();

    private String refreshToken;

    @Column(name = "token_expiry")
    private LocalDateTime tokenExpiry;


    public User() {}

    public User(Email email, PhoneNumber phone, UserName name, String rawPassword) {
        this.email = Objects.requireNonNull(email, "Email cannot be null");
        this.phone = Objects.requireNonNull(phone, "Phone cannot be null");
        this.name = Objects.requireNonNull(name, "Name cannot be null");
        this.password = rawPassword;
        this.type = UserType.CUSTOMER;
        this.userStatus = UserStatus.ACTIVE;
    }

    public void addUserType(UserType userType){
        this.type = userType;
    }

    public void setProfileImage(String profImgUrl){
        this.profileImageUrl=profileImageUrl;
    }

    public void updateLastLogin(LocalDateTime lastLogin){
        this.lastLogin=lastLogin;
    }

    public void changeUserStatus(UserStatus status){
        this.userStatus =status;
    }

    public void updateNewPassword(String password){
        this.password=password;
    }

    public void addBooking(Booking booking) {
        Objects.requireNonNull(booking, "Booking cannot be null");
        validateBookingAddition(booking);
        bookings.add(booking);
    }

    private void validateBookingAddition(Booking booking) {
        if (!isActive()) {
            throw new InactiveUserException("Cannot add booking for inactive user");
        }
    }

    private boolean isActive() {
        return this.userStatus == UserStatus.ACTIVE;
    }

    public static class InactiveUserException extends RuntimeException{
        public InactiveUserException(String message){
            super(message);
        }
    }
}
