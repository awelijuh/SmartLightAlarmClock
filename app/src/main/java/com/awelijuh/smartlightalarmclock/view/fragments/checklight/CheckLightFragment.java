package com.awelijuh.smartlightalarmclock.view.fragments.checklight;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.awelijuh.schemagenerator.GeneratedUI;
import com.awelijuh.smartlightalarmclock.databinding.FragmentCheckBulbBinding;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CheckLightFragment extends Fragment {

    @Inject
    CheckLightViewModel checkLightViewModel;

    private FragmentCheckBulbBinding binding;

    private GeneratedUI generatedUI;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCheckBulbBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        generatedUI = new GeneratedUI(requireContext(), binding.lightCheckContainer);
        checkLightViewModel.loadSchema();

        checkLightViewModel.schema.observe(getViewLifecycleOwner(), schema -> {
            if (schema != null) {
                generatedUI.build(schema);
            }
        });

        binding.testButton.setOnClickListener(v -> {
            checkLightViewModel.onTestButtonClick(generatedUI.getMapResult());
        });
    }
}
