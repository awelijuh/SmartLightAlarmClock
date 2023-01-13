package com.awelijuh.smartlightalarmclock.adapters.memory;

import static com.awelijuh.smartlightalarmclock.adapters.memory.PreferenceConstants.ALARMS_LIST;

import android.content.SharedPreferences;

import com.awelijuh.smartlightalarmclock.core.domain.AlarmClockItem;
import com.awelijuh.smartlightalarmclock.core.ports.out.AlarmPreferencePort;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import javax.inject.Inject;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;

@NoArgsConstructor(onConstructor = @__(@Inject))
public class AlarmPreferenceAdapter implements AlarmPreferencePort {

    @Inject
    SharedPreferences sharedPreferences;

    @Inject
    ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    public List<AlarmClockItem> getAlarms() {
        JavaType javaType = objectMapper.getTypeFactory().constructCollectionType(List.class, AlarmClockItem.class);
        return objectMapper.readValue(sharedPreferences.getString(ALARMS_LIST, "[]"), javaType);
    }

    @SneakyThrows
    @Override
    public void saveAlarms(List<AlarmClockItem> alarmClockItems) {
        sharedPreferences.edit()
                .putString(ALARMS_LIST, objectMapper.writeValueAsString(alarmClockItems))
                .apply();
    }
}
