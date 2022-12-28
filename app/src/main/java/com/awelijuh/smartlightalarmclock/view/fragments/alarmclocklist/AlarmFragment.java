package com.awelijuh.smartlightalarmclock.view.fragments.alarmclocklist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.awelijuh.smartlightalarmclock.R;
import com.awelijuh.smartlightalarmclock.databinding.FragmentAlarmBinding;
import com.awelijuh.smartlightalarmclock.view.fragments.alarmclocklist.adapter.AlarmClockRecyclerAdapter;
import com.awelijuh.smartlightalarmclock.domain.AlarmClockItem;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AlarmFragment extends Fragment {

    @Inject
    AlarmClockRecyclerAdapter alarmClockRecyclerAdapter;

    @Inject
    AlarmViewModel alarmViewModel;

    private FragmentAlarmBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentAlarmBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding.alarms.setAdapter(alarmClockRecyclerAdapter);
        binding.alarms.setLayoutManager(new LinearLayoutManager(requireContext()));
        alarmViewModel.alarms.observe(getViewLifecycleOwner(), r -> alarmClockRecyclerAdapter.updateList(r));

        binding.fab.setOnClickListener(e -> NavHostFragment.findNavController(AlarmFragment.this)
                .navigate(R.id.action_alarmFragment_to_alarmCreatorFragment));
        test();
    }

    void test() {
        List<AlarmClockItem> items = List.of(
                new AlarmClockItem().setActive(false).setTime(LocalTime.of(12, 20)),
                new AlarmClockItem().setActive(false).setTime(LocalTime.of(4, 45)).setReplay(Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY)),
                new AlarmClockItem().setActive(false).setTime(LocalTime.of(19, 26)).setReplay(Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY)),
                new AlarmClockItem().setActive(false).setTime(LocalTime.of(13, 54)).setReplay(Arrays.stream(DayOfWeek.values()).collect(Collectors.toSet()))
        );
        alarmViewModel.alarms.setValue(items);
    }

}