package com.awelijuh.smartlightalarmclock.view.fragments.alarmclocklist;

import com.awelijuh.smartlightalarmclock.domain.AlarmClockItem;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class AlarmViewModel extends ViewModel {

    public final MutableLiveData<List<AlarmClockItem>> alarms = new MutableLiveData<>(List.of());

    @Inject
    AlarmViewModel() {
    }

}