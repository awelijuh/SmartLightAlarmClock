package com.awelijuh.smartlightalarmclock.view.fragments.bulb;

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
import com.awelijuh.smartlightalarmclock.adapters.database.dao.BulbDao;
import com.awelijuh.smartlightalarmclock.core.ports.in.LedUseCase;
import com.awelijuh.smartlightalarmclock.databinding.FragmentBulbBinding;
import com.awelijuh.smartlightalarmclock.view.fragments.bulb.adapter.LightRecyclerAdapter;
import com.awelijuh.smartlightalarmclock.view.fragments.checklight.CheckLightViewModel;

import java.util.Map;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BulbFragment extends Fragment {

    @Inject
    BulbViewModel bulbViewModel;

    @Inject
    CheckLightViewModel checkLightViewModel;

    @Inject
    LightRecyclerAdapter adapter;

    @Inject
    Map<String, LedUseCase> leds;
    @Inject
    BulbDao bulbDao;
    private FragmentBulbBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentBulbBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        bulbViewModel.init();

        binding.bulbs.setAdapter(adapter);
        binding.bulbs.setLayoutManager(new LinearLayoutManager(requireContext()));
        bulbDao.getAllLiveData().observe(getViewLifecycleOwner(), r -> adapter.update(r));

        binding.fab.setOnClickListener(v -> {
            NavHostFragment.findNavController(BulbFragment.this)
                    .navigate(R.id.action_lightFragment_to_lightCreateFragment);
        });

        bulbViewModel.selectedBulb.observe(getViewLifecycleOwner(), light -> {
            if (light == null) {
                return;
            }
            checkLightViewModel.bulb.setValue(light);
            NavHostFragment.findNavController(BulbFragment.this)
                    .navigate(R.id.action_lightFragment_to_checkLightFragment);
        });

    }
}