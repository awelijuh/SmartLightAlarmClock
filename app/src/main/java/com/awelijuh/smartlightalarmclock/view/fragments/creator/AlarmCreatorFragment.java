package com.awelijuh.smartlightalarmclock.view.fragments.creator;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.awelijuh.smartlightalarmclock.R;
import com.awelijuh.smartlightalarmclock.core.ports.LedPort;
import com.awelijuh.smartlightalarmclock.databinding.FragmentCreateAlarmBinding;

import java.time.LocalTime;
import java.util.Set;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AlarmCreatorFragment extends Fragment {

    @Inject
    AlarmCreatorViewModel alarmCreatorViewModel;

    private FragmentCreateAlarmBinding binding;

    @Inject
    Set<LedPort> ledPorts;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateAlarmBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d("TEST_TAG", "size=" + ledPorts.size());
        binding.alarmTime.setIs24HourView(true);
        alarmCreatorViewModel.time.observe(getViewLifecycleOwner(), e -> {
            binding.alarmTime.setHour(e.getHour());
            binding.alarmTime.setMinute(e.getMinute());
        });
        alarmCreatorViewModel.periodText.observe(getViewLifecycleOwner(), e -> binding.currentReplay.setText(e));

        binding.alarmTime.setOnTimeChangedListener((timePicker, i, i1) ->
                alarmCreatorViewModel.updateTime(LocalTime.of(timePicker.getHour(), timePicker.getMinute())));

        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menu.clear();
                menu.add("ok").setIcon(R.drawable.ic_baseline_check_24).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if ("ok".contentEquals(menuItem.getTitle())) {
                    NavHostFragment.findNavController(AlarmCreatorFragment.this)
                            .navigateUp();
                    return true;
                }
                return false;
            }
        }, getViewLifecycleOwner());

    }
}
