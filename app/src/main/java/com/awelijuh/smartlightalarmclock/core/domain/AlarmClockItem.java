package com.awelijuh.smartlightalarmclock.core.domain;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;
import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AlarmClockItem {

    private String id = UUID.randomUUID().toString();

    private LocalTime time;

    private boolean isActive;

    private Set<DayOfWeek> replay;

}
