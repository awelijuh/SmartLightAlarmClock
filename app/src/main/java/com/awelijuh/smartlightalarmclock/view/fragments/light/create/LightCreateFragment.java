package com.awelijuh.smartlightalarmclock.view.fragments.light.create;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.awelijuh.schemagenerator.GeneratedUI;
import com.awelijuh.smartlightalarmclock.R;
import com.awelijuh.smartlightalarmclock.core.ports.in.LedUseCase;
import com.awelijuh.smartlightalarmclock.databinding.FragmentLightCreateBinding;
import com.awelijuh.smartlightalarmclock.view.fragments.light.LightViewModel;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LightCreateFragment extends Fragment {

    @Inject
    LightCreateViewModel lightCreateViewModel;

    @Inject
    LightViewModel lightViewModel;

    private FragmentLightCreateBinding binding;

    private Map<String, LedUseCase> lights;

    private List<String> lightNames;

    private GeneratedUI generatedUI;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLightCreateBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Inject
    void initLights(Set<LedUseCase> ledUseCaseSet) {
        this.lights = ledUseCaseSet.stream().collect(Collectors.toMap(LedUseCase::getName, Function.identity()));
        this.lightNames = ledUseCaseSet.stream().map(LedUseCase::getName).collect(Collectors.toList());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        generatedUI = new GeneratedUI(requireContext(), binding.credentialsLayout);

        binding.spinnerType.setAdapter(new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, lightNames));
        binding.spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                lightCreateViewModel.selectType((String) binding.spinnerType.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        lightCreateViewModel.type.observe(getViewLifecycleOwner(), e -> binding.spinnerType.setSelection(lightNames.indexOf(e)));

        lightCreateViewModel.type.observe(getViewLifecycleOwner(), e -> {
            if (e != null) {
                generatedUI.build(lights.get(e).getDeviceClass());
            }
        });

        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menu.clear();
                menu.add("ok").setIcon(R.drawable.ic_baseline_check_24).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getTitle() != null && "ok".contentEquals(menuItem.getTitle())) {
                    lightCreateViewModel.name.setValue(binding.nameEdittext.getText().toString());
                    lightCreateViewModel.device.setValue(generatedUI.getResult(lights.get(lightCreateViewModel.type.getValue()).getDeviceClass()));
                    lightViewModel.saveLightItem(lightCreateViewModel.createItem());
                    NavHostFragment.findNavController(LightCreateFragment.this)
                            .navigateUp();
                    return true;
                }
                return false;
            }
        }, getViewLifecycleOwner());
    }

}
