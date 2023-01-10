package com.awelijuh.smartlightalarmclock.view.fragments.alarmclocklist;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.awelijuh.smartlightalarmclock.adapters.memory.AlarmPreferenceAdapter;
import com.awelijuh.smartlightalarmclock.core.domain.AlarmClockItem;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import lombok.NoArgsConstructor;

@HiltViewModel
@NoArgsConstructor(onConstructor = @__(@Inject))
public class AlarmViewModel extends ViewModel {

    public final MutableLiveData<List<AlarmClockItem>> alarms = new MutableLiveData<>(List.of());

    public final MutableLiveData<AlarmClockItem> editAlarm = new MutableLiveData<>(null);

    public final MutableLiveData<Boolean> selectedMode = new MutableLiveData<>(false);

    public final MutableLiveData<Set<String>> selectedAlarms = new MutableLiveData<>(new HashSet<>());

    @Inject
    AlarmPreferenceAdapter alarmPreferencePort;

    private void updateAlarms(List<AlarmClockItem> newAlarms) {
        newAlarms = newAlarms.stream().sorted(Comparator.comparing(AlarmClockItem::getTime).thenComparing(AlarmClockItem::getId)).collect(Collectors.toList());
        alarmPreferencePort.saveAlarms(newAlarms);
        loadAlarms();
    }

    public void saveAlarmItem(AlarmClockItem alarmClockItem) {
        var map = alarms.getValue().stream().collect(Collectors.toMap(AlarmClockItem::getId, Function.identity()));
        map.put(alarmClockItem.getId(), alarmClockItem);
        var newAlarms = map.values().stream().sorted(Comparator.comparing(AlarmClockItem::getTime).thenComparing(AlarmClockItem::getId)).collect(Collectors.toList());
        updateAlarms(newAlarms);
    }

    public void clear() {
        editAlarm.setValue(null);
        alarms.setValue(List.of());
        selectedMode.setValue(false);
        selectedAlarms.setValue(new HashSet<>());
    }

    public void loadAlarms() {
        var newAlarms = alarmPreferencePort.getAlarms();
        if (!newAlarms.equals(alarms.getValue())) {
            alarms.setValue(alarmPreferencePort.getAlarms());
        }
    }

    public void init() {
        clear();
        loadAlarms();
    }

    public void selectAlarmItem(AlarmClockItem alarmClockItem) {
        if (Boolean.TRUE.equals(selectedMode.getValue())) {
            Set<String> a = new HashSet<>();
            if (selectedAlarms.getValue() != null) {
                a.addAll(selectedAlarms.getValue());
            }
            if (a.contains(alarmClockItem.getId())) {
                a.remove(alarmClockItem.getId());
            } else {
                a.add(alarmClockItem.getId());
            }
            selectedAlarms.setValue(a);
        } else {
            editAlarm.setValue(alarmClockItem);
        }
    }

    public void selectAllAlarmItems() {
        if (!Boolean.TRUE.equals(selectedMode.getValue())) {
            return;
        }
        if (selectedAlarms.getValue().size() == alarms.getValue().size()) {
            selectedAlarms.setValue(new HashSet<>());
        } else {
            selectedAlarms.setValue(alarms.getValue().stream().map(AlarmClockItem::getId).collect(Collectors.toSet()));
        }
    }

    public void setSelectedMode(AlarmClockItem alarmClockItem) {
        selectedAlarms.setValue(new HashSet<>());
        selectedMode.setValue(true);
        selectAlarmItem(alarmClockItem);
    }

    public void checkAlarm(AlarmClockItem alarmClockItem, boolean b) {
        Set<String> selected = new HashSet<>(selectedAlarms.getValue());
        if (b) {
            selected.add(alarmClockItem.getId());
        } else {
            selected.remove(alarmClockItem.getId());
        }
        if (!selected.equals(selectedAlarms.getValue())) {
            selectedAlarms.setValue(selected);
        }
    }

    public void removeSelected() {
        var newAlarms = alarms.getValue().stream().filter(e -> !selectedAlarms.getValue().contains(e.getId())).collect(Collectors.toList());
        updateAlarms(newAlarms);
        selectedMode.setValue(false);
    }

}