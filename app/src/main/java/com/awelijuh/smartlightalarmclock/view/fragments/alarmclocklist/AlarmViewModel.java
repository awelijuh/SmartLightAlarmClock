package com.awelijuh.smartlightalarmclock.view.fragments.alarmclocklist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.awelijuh.smartlightalarmclock.adapters.database.dao.AlarmClockDao;
import com.awelijuh.smartlightalarmclock.adapters.database.domain.AlarmClock;
import com.awelijuh.smartlightalarmclock.view.fragments.alarmclocklist.adapter.AlarmClockMapper;
import com.awelijuh.smartlightalarmclock.view.fragments.alarmclocklist.adapter.AlarmClockViewDto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.Getter;
import lombok.NoArgsConstructor;

@HiltViewModel
@NoArgsConstructor(onConstructor = @__(@Inject))
public class AlarmViewModel extends ViewModel {

    public final MutableLiveData<AlarmClock> editAlarm = new MutableLiveData<>(null);

    public final MutableLiveData<Boolean> selectedMode = new MutableLiveData<>(false);

    public final MutableLiveData<Set<Long>> selectedAlarms = new MutableLiveData<>(new HashSet<>());
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    public MediatorLiveData<List<AlarmClockViewDto>> alarmsDto = new MediatorLiveData<>();
    @Inject
    AlarmClockDao alarmClockDao;

    @Inject
    AlarmClockMapper alarmClockMapper;

    @Getter
    private LiveData<List<AlarmClock>> alarms;

    @Inject
    void injectAlarms(AlarmClockDao alarmClockDao) {
        alarms = alarmClockDao.getAllLiveData();

        alarmsDto.addSource(alarms, alarmClocks -> {
            alarmsDto.setValue(alarmClockMapper.map(alarmClocks));
        });


    }

    public void saveAlarmItem(AlarmClock alarmClock) {
        alarmClockDao.save(alarmClock)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public void clear() {
        editAlarm.setValue(null);
        selectedMode.setValue(false);
        selectedAlarms.setValue(new HashSet<>());
    }

    public void init() {
        clear();
    }

    public void selectAlarmItem(AlarmClock alarmClockItem) {
        if (Boolean.TRUE.equals(selectedMode.getValue())) {
            Set<Long> a = new HashSet<>();
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
            selectedAlarms.setValue(alarms.getValue().stream().map(AlarmClock::getId).collect(Collectors.toSet()));
        }
    }

    public void setSelectedMode(AlarmClock alarmClock) {
        selectedAlarms.setValue(new HashSet<>());
        selectedMode.setValue(true);
        selectAlarmItem(alarmClock);
    }

    public void checkAlarm(AlarmClock alarmClock, boolean b) {
        Set<Long> selected = new HashSet<>(selectedAlarms.getValue());
        if (b) {
            selected.add(alarmClock.getId());
        } else {
            selected.remove(alarmClock.getId());
        }
        if (!selected.equals(selectedAlarms.getValue())) {
            selectedAlarms.setValue(selected);
        }
    }

    public void removeSelected() {
        compositeDisposable.add(
                alarmClockDao.delete(selectedAlarms.getValue())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> selectedMode.setValue(false))
        );
    }

}