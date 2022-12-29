package com.awelijuh.smartlightalarmclock.view.utils;

import android.content.Context;

import com.awelijuh.smartlightalarmclock.R;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.Getter;

public class AlarmUtils {

    public static final Set<DayOfWeek> WEEKDAYS = Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY);

    public static String getPeriodText(Set<DayOfWeek> replay, Context context) {
        if (replay == null || replay.isEmpty()) {
            return context.getString(R.string.once_period);
        }
        if (replay.size() == 7) {
            return context.getString(R.string.every_day_period);
        }
        if (replay.equals(WEEKDAYS)) {
            return context.getString(R.string.weekdays_period);
        } else {
            List<DayOfWeek> days = Arrays.stream(DayOfWeek.values()).collect(Collectors.toList());
            String[] daysStr = context.getResources().getStringArray(R.array.weekdays_short);

            return replay.stream().sorted(Comparator.comparingInt(days::indexOf))
                    .map(e -> daysStr[(days.indexOf(e))]).collect(Collectors.joining(" "));
        }
    }

    public static boolean[] toPrimitiveArray(final List<Boolean> booleanList) {
        final boolean[] primitives = new boolean[booleanList.size()];
        int index = 0;
        for (Boolean object : booleanList) {
            primitives[index++] = object;
        }
        return primitives;
    }


}
