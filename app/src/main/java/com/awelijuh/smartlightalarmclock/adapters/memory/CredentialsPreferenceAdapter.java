package com.awelijuh.smartlightalarmclock.adapters.memory;

import android.content.SharedPreferences;

import com.awelijuh.smartlightalarmclock.core.ports.in.LedUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

import javax.inject.Inject;

import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import static com.awelijuh.smartlightalarmclock.adapters.memory.PreferenceConstants.CREDENTIALS_PREFIX;

@NoArgsConstructor(onConstructor = @__(@Inject))
public class CredentialsPreferenceAdapter {

    @Inject
    SharedPreferences sharedPreferences;

    @Inject
    ObjectMapper objectMapper;

    @Inject
    Map<String, LedUseCase> leds;

    @SneakyThrows
    public void saveCredentials(String name, Object credentials) {
        sharedPreferences.edit()
                .putString(CREDENTIALS_PREFIX + name, objectMapper.writeValueAsString(credentials))
                .apply();
    }

    @SneakyThrows
    public <T> T getCredentials(String name) {
        String o = sharedPreferences.getString(CREDENTIALS_PREFIX + name, null);
        if (o == null) {
            return null;
        }
        return (T) objectMapper.readValue(o, leds.get(name).getCredentialsClass());
    }

}
