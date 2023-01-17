package com.awelijuh.smartlightalarmclock.core.services;

import com.awelijuh.smartlightalarmclock.core.ports.in.LedUseCase;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class LedModule {

    @Provides
    Map<String, LedUseCase> provideNameToLed(Set<LedUseCase> leds) {
        return leds.stream().collect(Collectors.toMap(LedUseCase::getKey, Function.identity()));
    }

}
