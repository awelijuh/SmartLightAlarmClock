package com.awelijuh.smartlightalarmclock.core.services;

import com.awelijuh.smartlightalarmclock.core.ports.in.LedUseCase;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.inject.Inject;

import dagger.MapKey;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import dagger.multibindings.IntoMap;
import dagger.multibindings.IntoSet;

@Module
@InstallIn(SingletonComponent.class)
public class LightServiceLocator {

    @Provides
    @IntoSet
    LedUseCase provideToyaService(ToyaLedService toyaLedService) {
        return toyaLedService;
    }

}
