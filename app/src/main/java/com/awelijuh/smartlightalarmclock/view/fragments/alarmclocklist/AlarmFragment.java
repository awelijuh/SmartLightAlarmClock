package com.awelijuh.smartlightalarmclock.view.fragments.alarmclocklist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.awelijuh.smartlightalarmclock.R;
import com.awelijuh.smartlightalarmclock.databinding.FragmentAlarmBinding;
import com.awelijuh.smartlightalarmclock.view.fragments.alarmclocklist.adapter.AlarmClockRecyclerAdapter;
import com.awelijuh.smartlightalarmclock.view.fragments.alarmclocklist.creator.AlarmCreatorViewModel;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

@AndroidEntryPoint
public class AlarmFragment extends Fragment {

    @Inject
    AlarmClockRecyclerAdapter alarmClockRecyclerAdapter;

    @Inject
    AlarmViewModel alarmViewModel;

    @Inject
    AlarmCreatorViewModel alarmCreatorViewModel;

    private FragmentAlarmBinding binding;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private androidx.appcompat.view.ActionMode actionMode;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentAlarmBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        alarmViewModel.init();

        binding.alarms.setAdapter(alarmClockRecyclerAdapter);
        binding.alarms.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.removeItem.setOnClickListener(v -> {
            alarmViewModel.removeSelected();
        });
        alarmViewModel.alarms.observe(getViewLifecycleOwner(), r -> alarmClockRecyclerAdapter.update());
        alarmViewModel.selectedAlarms.observe(getViewLifecycleOwner(), r -> {
            alarmClockRecyclerAdapter.update();
            setupActionModeTitle();
        });
        alarmViewModel.selectedMode.observe(getViewLifecycleOwner(), r -> {
            binding.removeContainer.setVisibility(r ? View.VISIBLE : View.GONE);
            alarmClockRecyclerAdapter.update();
        });

        alarmViewModel.selectedMode.observe(getViewLifecycleOwner(), selectedMode -> {
            if (!selectedMode && actionMode != null) {
                actionMode.finish();
            } else if (selectedMode) {
                actionMode = ((AppCompatActivity) requireActivity()).startSupportActionMode(new ActionMode.Callback() {
                    @Override
                    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                        setupActionModeTitle();
                        binding.fab.hide();
                        menu.clear();
                        MenuItem allItem = menu.add("select all").setIcon(R.drawable.ic_baseline_checklist_24);
                        allItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
                        allItem.setOnMenuItemClickListener(menuItem -> {
                            alarmViewModel.selectAllAlarmItems();
                            return true;
                        });
                        return true;
                    }

                    @Override
                    public boolean onPrepareActionMode(androidx.appcompat.view.ActionMode actionMode, Menu menu) {
                        return false;
                    }

                    @Override
                    public boolean onActionItemClicked(androidx.appcompat.view.ActionMode actionMode, MenuItem menuItem) {
                        return false;
                    }

                    @Override
                    public void onDestroyActionMode(androidx.appcompat.view.ActionMode actionMode) {
                        binding.fab.show();
                        AlarmFragment.this.actionMode = null;
                        alarmViewModel.selectedMode.setValue(false);
                    }
                });
            }
        });


        binding.fab.setOnClickListener(e -> {
            alarmCreatorViewModel.clear();
            NavHostFragment.findNavController(AlarmFragment.this)
                    .navigate(R.id.action_alarmFragment_to_alarmCreatorFragment);
        });

        alarmViewModel.editAlarm.observe(getViewLifecycleOwner(), alarm -> {
            if (alarm == null) {
                return;
            }
            alarmCreatorViewModel.setupUpdate(alarm);
            NavHostFragment.findNavController(AlarmFragment.this)
                    .navigate(R.id.action_alarmFragment_to_alarmCreatorFragment);
        });

    }

    private void setupActionModeTitle() {
        if (actionMode == null) {
            return;
        }
        int count = alarmViewModel.selectedAlarms.getValue().size();
        if (count == 0) {
            binding.removeItem.setClickable(false);
            binding.removeText.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray_600));
            actionMode.setTitle(R.string.select_objects);
        } else {
            binding.removeItem.setClickable(true);
            binding.removeText.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
            actionMode.setTitle(String.join(" ", getString(R.string.selected_objects), String.valueOf(count)));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
        if (actionMode != null) {
            actionMode.finish();
        }
    }
}