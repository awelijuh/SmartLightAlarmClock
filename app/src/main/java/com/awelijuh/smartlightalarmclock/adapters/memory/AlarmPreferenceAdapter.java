package com.awelijuh.smartlightalarmclock.adapters.memory;

import android.content.SharedPreferences;

import com.awelijuh.smartlightalarmclock.core.domain.AlarmClockItem;
import com.awelijuh.smartlightalarmclock.core.ports.out.AlarmPreferencePort;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.android.components.FragmentComponent;
import dagger.hilt.android.components.ViewModelComponent;
import dagger.hilt.components.SingletonComponent;
import lombok.SneakyThrows;

import static com.awelijuh.smartlightalarmclock.adapters.memory.PreferenceConstants.ALARMS_LIST;

//@Module
//@InstallIn({FragmentComponent.class, ActivityComponent.class, ViewModelComponent.class, SingletonComponent.class})
@Singleton
public class AlarmPreferenceAdapter implements AlarmPreferencePort {

//    @Inject
//    SharedPreferences sharedPreferences;
//
//    @Inject
//    ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    public List<AlarmClockItem> getAlarms() {
//        return objectMapper.readValue(sharedPreferences.getString(ALARMS_LIST, "[]"), new TypeReference<>() {
//        });
        return List.of();
    }

    @SneakyThrows
    @Override
    public void saveAlarms(List<AlarmClockItem> alarmClockItems) {
//        sharedPreferences.edit()
//                .putString(ALARMS_LIST, objectMapper.writeValueAsString(alarmClockItems))
//                .apply();
    }
}
