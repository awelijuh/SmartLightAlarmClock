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
import dagger.multibindings.IntoSet;

@Module
@InstallIn(SingletonComponent.class)
public class LightServiceLocator {

    @Provides
    @IntoSet
    LedUseCase provideToyaService(ToyaLedService toyaLedService) {
        return toyaLedService;
    }

    @Provides
    Map<String, LedUseCase> provideNameToLed(Set<LedUseCase> leds) {
        return leds.stream().collect(Collectors.toMap(LedUseCase::getKey, Function.identity()));
    }

}
