package com.awelijuh.smartlightalarmclock.view.fragments.alarmclocklist.adapter;

import android.content.Context;

import com.awelijuh.smartlightalarmclock.adapters.database.domain.AlarmClock;
import com.awelijuh.smartlightalarmclock.view.fragments.alarmclocklist.AlarmViewModel;
import com.awelijuh.smartlightalarmclock.view.utils.AlarmUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class AlarmClockMapper {

    @Inject
    ObjectMapper objectMapper;

    @Inject
    @ApplicationContext
    Context context;

    @Inject
    AlarmViewModel alarmViewModel;

    public AlarmClockViewDto map(AlarmClock item) {
        var result = objectMapper.convertValue(item, AlarmClockViewDto.class);

        result.setPeriodText(AlarmUtils.getPeriodText(result.getReplay(), context));
        result.setSelectedMode(Boolean.TRUE.equals(alarmViewModel.selectedMode.getValue()));
        result.setSelected(alarmViewModel.selectedAlarms.getValue().contains(result.getId()));

        return result;
    }

    public List<AlarmClockViewDto> map(List<AlarmClock> items) {
        if (items == null) {
            return new ArrayList<>();
        }
        return items.stream().map(this::map).collect(Collectors.toList());
    }

}
