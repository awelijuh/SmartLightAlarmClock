package com.awelijuh.smartlightalarmclock.fragments.alarm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.awelijuh.smartlightalarmclock.databinding.FragmentAlarmBinding;

import javax.inject.Inject;

public class AlarmFragment extends Fragment {

    @Inject
    private AlarmViewModel mViewModel;

    private FragmentAlarmBinding alarmBinding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        alarmBinding = FragmentAlarmBinding.inflate(inflater, container, false);
        return alarmBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }
}