package com.awelijuh.smartlightalarmclock.view.fragments.light;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.awelijuh.smartlightalarmclock.adapters.memory.LightPreferenceAdapter;
import com.awelijuh.smartlightalarmclock.core.domain.Light;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import lombok.NoArgsConstructor;

@HiltViewModel
@NoArgsConstructor(onConstructor = @__(@Inject))
public class LightViewModel extends ViewModel {

    public final MutableLiveData<List<Light>> lights = new MutableLiveData<>(List.of());

    public final MutableLiveData<Light> selectedLight = new MutableLiveData<>(null);

    @Inject
    LightPreferenceAdapter lightPreferenceAdapter;

    private void updateLights(List<Light> newAlarms) {
        newAlarms = newAlarms.stream().sorted(Comparator.comparing(Light::getName).thenComparing(Light::getId)).collect(Collectors.toList());
        lightPreferenceAdapter.saveLights(newAlarms);
        loadLights();
    }

    public void saveLightItem(Light light) {
        var map = lights.getValue().stream().collect(Collectors.toMap(Light::getId, Function.identity()));
        map.put(light.getId(), light);
        var newAlarms = map.values().stream().sorted(Comparator.comparing(Light::getName).thenComparing(Light::getId)).collect(Collectors.toList());
        updateLights(newAlarms);
    }

    public void clear() {
        lights.setValue(List.of());
        selectedLight.setValue(null);
    }

    public void loadLights() {
        var newLights = lightPreferenceAdapter.getLights();
        if (!newLights.equals(lights.getValue())) {
            lights.setValue(newLights);
        }
    }

    public void init() {
        clear();
        loadLights();
    }

}