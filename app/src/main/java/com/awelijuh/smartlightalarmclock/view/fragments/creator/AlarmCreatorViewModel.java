package com.awelijuh.smartlightalarmclock.view.fragments.creator;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.awelijuh.smartlightalarmclock.view.utils.AlarmUtils;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class AlarmCreatorViewModel extends AndroidViewModel {

    public final MutableLiveData<LocalTime> time = new MutableLiveData<>();

    public final MutableLiveData<Set<DayOfWeek>> period = new MutableLiveData<>();

    public final LiveData<String> periodText;

    @Inject
    public AlarmCreatorViewModel(@NonNull Application application) {
        super(application);
        this.periodText = Transformations.map(period, period -> AlarmUtils.getPeriodText(period, application));
    }

    public void updateTime(LocalTime time) {
        this.time.setValue(time);
    }
}
