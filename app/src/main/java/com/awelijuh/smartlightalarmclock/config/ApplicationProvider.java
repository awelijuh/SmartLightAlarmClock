package com.awelijuh.smartlightalarmclock.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class ApplicationProvider {

//    @Provides
//    public ObjectMapper provideObjectMapper() {
//        return new ObjectMapper();
//    }

}
