package com.awelijuh.smartlightalarmclock.view.fragments.alarmclocklist.edit;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.awelijuh.smartlightalarmclock.adapters.database.domain.AlarmClock;
import com.awelijuh.smartlightalarmclock.view.utils.AlarmUtils;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;
import lombok.Getter;
import lombok.NoArgsConstructor;

@HiltViewModel
@NoArgsConstructor(onConstructor = @__(@Inject))
public class AlarmEditViewModel extends ViewModel {

    public final MutableLiveData<LocalTime> time = new MutableLiveData<>();

    public final MutableLiveData<Set<DayOfWeek>> period = new MutableLiveData<>();

    public final MutableLiveData<Long> alarmId = new MutableLiveData<>(null);

    public final LiveData<Boolean> isUpdate = Transformations.map(alarmId, Objects::nonNull);

    @Getter
    private LiveData<String> periodText;

    @Inject
    void init(@ApplicationContext Context context) {
        this.periodText = Transformations.map(period, period -> AlarmUtils.getPeriodText(period, context));
    }

    public void updateTime(LocalTime time) {
        this.time.setValue(time);
    }

    public AlarmClock createItem() {
        AlarmClock alarmClockItem = new AlarmClock();
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

    public void setupUpdate(AlarmClock v) {
        time.setValue(LocalTime.of(v.getTime().getHour(), v.getTime().getMinute()));
        period.setValue(new HashSet<>(v.getReplay()));
        alarmId.setValue(v.getId());
    }
}
