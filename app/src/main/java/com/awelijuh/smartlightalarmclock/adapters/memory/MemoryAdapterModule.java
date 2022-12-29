package com.awelijuh.smartlightalarmclock.adapters.memory;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class MemoryAdapterModule {

    @Provides
    public SharedPreferences provideSharedPreferences(@ApplicationContext Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

}
