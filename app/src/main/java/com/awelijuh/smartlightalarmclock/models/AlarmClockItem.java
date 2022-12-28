package com.awelijuh.smartlightalarmclock.models;

import android.content.Context;

import com.awelijuh.smartlightalarmclock.R;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.Data;
import lombok.Setter;

@Data
public class AlarmClockItem {

    public LocalTime time;

    public Boolean isActive;

    public Set<DayOfWeek> replay;

    public String getPeriodText(Context context) {
        if (replay == null) {
            return context.getString(R.string.once_period);
        }
        if (replay.size() == 7) {
            return context.getString(R.string.every_day_period);
        }
        if (replay.equals(Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY))) {
            return context.getString(R.string.weekdays_period);
        } else {
            List<DayOfWeek> days = Arrays.stream(DayOfWeek.values()).collect(Collectors.toList());
            String[] daysStr = context.getResources().getStringArray(R.array.weekdays);

            return replay.stream().sorted(Comparator.comparingInt(days::indexOf))
                    .map(e -> daysStr[(days.indexOf(e))]).collect(Collectors.joining(" "));
        }
    }

}
