package com.awelijuh.smartlightalarmclock.core.ports.out;

import com.awelijuh.smartlightalarmclock.core.domain.AlarmClockItem;

import java.util.List;

public interface AlarmPreferencePort {

    List<AlarmClockItem> getAlarms();

    void saveAlarms(List<AlarmClockItem> alarmClockItems);

}
