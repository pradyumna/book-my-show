package org.book_my_show.domain.status;

import lombok.Getter;

import java.time.LocalTime;

@Getter
public enum ShowTimeSlot {
    MORNING(LocalTime.of(6, 0), LocalTime.of(11, 59)),
    AFTERNOON(LocalTime.of(12, 0), LocalTime.of(15, 59)),
    EVENING(LocalTime.of(16, 0), LocalTime.of(19, 59)),
    NIGHT(LocalTime.of(20, 0), LocalTime.of(23, 59));

    private final LocalTime startTime;
    private final LocalTime endTime;

    ShowTimeSlot(LocalTime startTime, LocalTime endTime) {
        this.startTime=startTime;
        this.endTime=endTime;
    }
}
