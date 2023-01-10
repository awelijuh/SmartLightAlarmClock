package com.awelijuh.smartlightalarmclock.view.fragments.alarmclocklist.adapter;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;

import lombok.Data;

@Data
public class AlarmClockViewDto {

    private String id;

    private LocalTime time;

    private boolean isActive;

    private Set<DayOfWeek> replay;

    private String periodText;

    private boolean selectedMode;

    private boolean selected;


}
