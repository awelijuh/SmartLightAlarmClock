package com.awelijuh.smartlightalarmclock.core.ports.out;

import com.awelijuh.smartlightalarmclock.core.domain.Light;

import java.util.List;

public interface LightPreferencePort {

    List<Light> getLights();

    void saveLights(List<Light> lights);

}
