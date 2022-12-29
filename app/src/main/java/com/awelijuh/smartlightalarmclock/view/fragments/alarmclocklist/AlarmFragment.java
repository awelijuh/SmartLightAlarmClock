package com.awelijuh.smartlightalarmclock.view.fragments.alarmclocklist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.awelijuh.smartlightalarmclock.R;
import com.awelijuh.smartlightalarmclock.adapters.memory.AlarmPreferenceAdapter;
import com.awelijuh.smartlightalarmclock.databinding.FragmentAlarmBinding;
import com.awelijuh.smartlightalarmclock.view.fragments.alarmclocklist.adapter.AlarmClockRecyclerAdapter;
import com.awelijuh.smartlightalarmclock.view.fragments.creator.AlarmCreatorViewModel;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
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

//    @Inject
//    AlarmPreferenceAdapter alarmPreferenceAdapter;

    private FragmentAlarmBinding binding;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();


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
        alarmViewModel.alarms.observe(getViewLifecycleOwner(), r -> alarmClockRecyclerAdapter.updateList(r));

        binding.fab.setOnClickListener(e -> {
            alarmCreatorViewModel.clear();
            NavHostFragment.findNavController(AlarmFragment.this)
                    .navigate(R.id.action_alarmFragment_to_alarmCreatorFragment);
        });

        alarmClockRecyclerAdapter.setOnItemClickListener(v -> {
            alarmCreatorViewModel.setupUpdate(v);
            NavHostFragment.findNavController(AlarmFragment.this)
                    .navigate(R.id.action_alarmFragment_to_alarmCreatorFragment);
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }
}