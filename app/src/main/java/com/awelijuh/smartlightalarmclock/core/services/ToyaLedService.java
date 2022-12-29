package com.awelijuh.smartlightalarmclock.core.services;

import com.awelijuh.smartlightalarmclock.core.ports.in.LedUseCase;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import dagger.multibindings.IntoSet;

@Module
@InstallIn(SingletonComponent.class)
public class ToyaLedService implements LedUseCase {

    @Provides
    @IntoSet
    public LedUseCase provideThis() {
        return this;
    }

}
