package com.awelijuh.smartlightalarmclock.models;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class AlarmClockItem {

    private LocalDateTime time;

    private boolean isActive;

}
