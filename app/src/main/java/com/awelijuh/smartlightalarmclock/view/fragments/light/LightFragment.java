package com.awelijuh.smartlightalarmclock.view.fragments.light;

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
import com.awelijuh.smartlightalarmclock.databinding.FragmentLightBinding;
import com.awelijuh.smartlightalarmclock.view.fragments.light.adapter.LightRecyclerAdapter;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LightFragment extends Fragment {

    @Inject
    LightViewModel lightViewModel;

    @Inject
    LightRecyclerAdapter adapter;

    private FragmentLightBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentLightBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        lightViewModel.init();

        binding.lights.setAdapter(adapter);
        binding.lights.setLayoutManager(new LinearLayoutManager(requireContext()));
        lightViewModel.lights.observe(getViewLifecycleOwner(), r -> adapter.update());

        binding.fab.setOnClickListener(v -> {
            NavHostFragment.findNavController(LightFragment.this)
                    .navigate(R.id.action_lightFragment_to_lightCreateFragment);
        });

    }
}