package com.awelijuh.smartlightalarmclock.adapters.database.domain;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;

import lombok.Data;

@Entity
@Data
public class AlarmClock {

    @PrimaryKey
    private Long id;

    private LocalTime time;

    private boolean isActive;

    private Set<DayOfWeek> replay;

}
