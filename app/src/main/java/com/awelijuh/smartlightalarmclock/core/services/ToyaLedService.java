package com.awelijuh.smartlightalarmclock.core.services;

import com.awelijuh.smartlightalarmclock.core.ports.LedPort;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import dagger.multibindings.IntoSet;

@Module
@InstallIn(SingletonComponent.class)
public class ToyaLedService implements LedPort {

    @Provides
    @IntoSet
    public LedPort provideThis() {
        return this;
    }

}
