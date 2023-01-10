package com.awelijuh.smartlightalarmclock.adapters.memory;

import static com.awelijuh.smartlightalarmclock.adapters.memory.PreferenceConstants.CREDENTIALS_PREFIX;

import android.content.SharedPreferences;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Inject;

import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

@NoArgsConstructor(onConstructor = @__(@Inject))
public class CredentialsPreferenceAdapter {

    @Inject
    SharedPreferences sharedPreferences;

    @Inject
    ObjectMapper objectMapper;

    @SneakyThrows
    public void saveCredentials(String name, Object credentials) {
        sharedPreferences.edit()
                .putString(CREDENTIALS_PREFIX + name, objectMapper.writeValueAsString(credentials))
                .apply();
    }

    public Object getCredentials(String name) {
        return sharedPreferences.getString(CREDENTIALS_PREFIX + name, null);
    }

    public <T> T getCredentials(String name, Class<T> tClass) {
        Object o = getCredentials(name);
        if (o == null) {
            return null;
        }
        return (T) o;
    }

}
