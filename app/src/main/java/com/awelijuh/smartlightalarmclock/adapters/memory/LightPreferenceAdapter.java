package com.awelijuh.smartlightalarmclock.adapters.memory;

import static com.awelijuh.smartlightalarmclock.adapters.memory.PreferenceConstants.LIGHTS_LIST;

import android.content.SharedPreferences;

import com.awelijuh.smartlightalarmclock.core.domain.Light;
import com.awelijuh.smartlightalarmclock.core.ports.in.LedUseCase;
import com.awelijuh.smartlightalarmclock.core.ports.out.LightPreferencePort;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;

import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

@NoArgsConstructor(onConstructor = @__(@Inject))
public class LightPreferenceAdapter implements LightPreferencePort {

    @Inject
    SharedPreferences sharedPreferences;

    @Inject
    ObjectMapper objectMapper;

    @Inject
    Map<String, LedUseCase> leds;

    @SneakyThrows
    @Override
    public List<Light> getLights() {
        JavaType javaType = objectMapper.getTypeFactory().constructCollectionType(List.class, Light.class);
        List<Light> lights = objectMapper.readValue(sharedPreferences.getString(LIGHTS_LIST, "[]"), javaType);
        return lights.stream()
                .filter(e -> leds.containsKey(e.getType()))
                .peek(e -> e.setCredentials(
                        objectMapper.convertValue(
                                e.getCredentials(),
                                leds.get(e.getType()).getCredentialsClass()
                        )
                ))
                .filter(e -> e.getCredentials() != null)
                .collect(Collectors.toList());
    }

    @SneakyThrows
    @Override
    public void saveLights(List<Light> lights) {
        sharedPreferences.edit()
                .putString(LIGHTS_LIST, objectMapper.writeValueAsString(lights))
                .apply();
    }
}
