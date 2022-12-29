package com.awelijuh.smartlightalarmclock.view.fragments.alarmclocklist;

import com.awelijuh.smartlightalarmclock.adapters.memory.AlarmPreferenceAdapter;
import com.awelijuh.smartlightalarmclock.core.domain.AlarmClockItem;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import dagger.hilt.android.lifecycle.HiltViewModel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@HiltViewModel
@NoArgsConstructor(onConstructor = @__(@Inject))
public class AlarmViewModel extends ViewModel {

    public final MutableLiveData<List<AlarmClockItem>> alarms = new MutableLiveData<>(List.of());

    public final MutableLiveData<AlarmClockItem> editAlarm = new MutableLiveData<>(null);

    @Inject
    AlarmPreferenceAdapter alarmPreferencePort;

    public void saveAlarmItem(AlarmClockItem alarmClockItem) {
        List<AlarmClockItem> alarmList = alarms.getValue();
        var map = alarmList.stream().collect(Collectors.toMap(AlarmClockItem::getId, Function.identity()));
        map.put(alarmClockItem.getId(), alarmClockItem);
        alarmList = map.values().stream().sorted(Comparator.comparing(AlarmClockItem::getTime)).collect(Collectors.toList());
        alarms.setValue(alarmList);
        alarmPreferencePort.saveAlarms(alarmList);
    }

    public void clear() {
        editAlarm.setValue(null);
        alarms.setValue(List.of());
    }

    public void loadAlarms() {
        alarms.setValue(alarmPreferencePort.getAlarms());
    }

    public void init() {
        clear();
        loadAlarms();
    }


}