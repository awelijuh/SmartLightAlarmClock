package com.awelijuh.smartlightalarmclock.view.fragments.creator;

import android.app.Application;

import com.awelijuh.smartlightalarmclock.core.domain.AlarmClockItem;
import com.awelijuh.smartlightalarmclock.view.utils.AlarmUtils;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class AlarmCreatorViewModel extends AndroidViewModel {

    public final MutableLiveData<LocalTime> time = new MutableLiveData<>();

    public final MutableLiveData<Set<DayOfWeek>> period = new MutableLiveData<>();

    public final MutableLiveData<String> alarmId = new MutableLiveData<>(null);

    public final LiveData<Boolean> isUpdate = Transformations.map(alarmId, Objects::nonNull);

    public final LiveData<String> periodText;

    @Inject
    public AlarmCreatorViewModel(@NonNull Application application) {
        super(application);
        this.periodText = Transformations.map(period, period -> AlarmUtils.getPeriodText(period, application));
    }

    public void updateTime(LocalTime time) {
        this.time.setValue(time);
    }

    public AlarmClockItem createItem() {
        AlarmClockItem alarmClockItem = new AlarmClockItem();
        alarmClockItem.setActive(true);
        alarmClockItem.setReplay(new HashSet<>());
        if (alarmId.getValue() != null) {
            alarmClockItem.setId(alarmId.getValue());
        }
        if (period.getValue() != null) {
            alarmClockItem.getReplay().addAll(period.getValue());
        }
        alarmClockItem.setTime(time.getValue());

        return alarmClockItem;
    }

    public void clear() {
        time.setValue(null);
        period.setValue(null);
        alarmId.setValue(null);
    }

    public void setupUpdate(AlarmClockItem v) {
        time.setValue(LocalTime.of(v.getTime().getHour(), v.getTime().getMinute()));
        period.setValue(new HashSet<>(v.getReplay()));
        alarmId.setValue(v.getId());
    }
}
