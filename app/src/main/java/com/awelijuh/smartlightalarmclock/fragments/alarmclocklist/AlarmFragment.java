package com.awelijuh.smartlightalarmclock.fragments.alarmclocklist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.awelijuh.smartlightalarmclock.databinding.FragmentAlarmBinding;
import com.awelijuh.smartlightalarmclock.fragments.alarmclocklist.adapter.AlarmClockRecyclerAdapter;
import com.awelijuh.smartlightalarmclock.models.AlarmClockItem;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AlarmFragment extends Fragment {

    @Inject
    AlarmClockRecyclerAdapter alarmClockRecyclerAdapter;

    @Inject
    AlarmViewModel mViewModel;

    private FragmentAlarmBinding alarmBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mViewModel = new ViewModelProvider(requireActivity()).get(AlarmViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        alarmBinding = FragmentAlarmBinding.inflate(inflater, container, false);
        return alarmBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        alarmBinding.alarms.setAdapter(alarmClockRecyclerAdapter);
        alarmBinding.alarms.setLayoutManager(new LinearLayoutManager(requireContext()));
        mViewModel.alarms.observe(getViewLifecycleOwner(), r -> alarmClockRecyclerAdapter.updateList(r));

        test();
    }

    void test() {
        List<AlarmClockItem> items = List.of(
                new AlarmClockItem().setIsActive(false).setTime(LocalTime.of(12, 20)),
                new AlarmClockItem().setIsActive(false).setTime(LocalTime.of(4, 45)).setReplay(Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY)),
                new AlarmClockItem().setIsActive(false).setTime(LocalTime.of(19, 26)).setReplay(Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY)),
                new AlarmClockItem().setIsActive(false).setTime(LocalTime.of(13, 54)).setReplay(Arrays.stream(DayOfWeek.values()).collect(Collectors.toSet()))
        );
        mViewModel.alarms.setValue(items);
    }

}