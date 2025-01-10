package org.book_my_show.domain.seat_lock;

import jakarta.persistence.*;
import org.book_my_show.domain.BaseEntity;

import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Table(name = "seat_locks",
        uniqueConstraints = @UniqueConstraint(columnNames = {"show_id", "seat_number"}))
public class SeatLock extends BaseEntity {
    @Column(name = "show_id", nullable = false)
    private Long showId;

    @Column(name = "seat_number", nullable = false)
    private String seatNumber;

    @Column(name = "locked_by")
    private Long userId;

    @Column(name = "locked_until")
    private LocalDateTime lockedUntil;

    protected SeatLock() {}

    public SeatLock(Long showId, String seatNumber, Long userId, Duration lockDuration) {
        this.showId = showId;
        this.seatNumber = seatNumber;
        this.userId = userId;
        this.lockedUntil = LocalDateTime.now().plus(lockDuration);
    }

    public boolean isLockExpired() {
        return LocalDateTime.now().isAfter(lockedUntil);
    }

    public String getSeatNumber(){return this.seatNumber;}
}
