package com.awelijuh.smartlightalarmclock.fragments.alarm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.awelijuh.smartlightalarmclock.databinding.FragmentAlarmBinding;
import com.awelijuh.smartlightalarmclock.fragments.alarm.list.AlarmClockListAdapter;
import com.awelijuh.smartlightalarmclock.models.AlarmClockItem;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AlarmFragment extends Fragment {

    public AlarmViewModel mViewModel;

    private FragmentAlarmBinding alarmBinding;

    @Inject
    private AlarmClockListAdapter alarmClockListAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AlarmViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        alarmBinding = FragmentAlarmBinding.inflate(inflater, container, false);
        return alarmBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        alarmBinding.alarms.setAdapter(alarmClockListAdapter);
    }
}