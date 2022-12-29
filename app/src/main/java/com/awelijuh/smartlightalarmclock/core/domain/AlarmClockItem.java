package com.awelijuh.smartlightalarmclock.core.domain;

import android.content.Context;

import com.awelijuh.smartlightalarmclock.view.utils.AlarmUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;

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

    @JsonIgnore
    private String periodText;

    @JsonIgnore
    public void setupPeriodText(Context context) {
        this.periodText = AlarmUtils.getPeriodText(replay, context);
    }
}
