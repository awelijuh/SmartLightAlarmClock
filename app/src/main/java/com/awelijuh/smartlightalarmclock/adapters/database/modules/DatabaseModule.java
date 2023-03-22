package com.awelijuh.smartlightalarmclock.adapters.database.modules;

import android.content.Context;

import androidx.room.Room;

import com.awelijuh.smartlightalarmclock.adapters.database.AppDatabase;
import com.awelijuh.smartlightalarmclock.adapters.database.dao.AlarmClockDao;
import com.awelijuh.smartlightalarmclock.adapters.database.dao.BulbDao;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DatabaseModule {

    @Provides
    AppDatabase provideDatabase(@ApplicationContext Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, "alarmClockDatabase")
                .build();
    }

    @Provides
    AlarmClockDao provideAlarmClockDao(AppDatabase database) {
        return database.alarmClockDao();
    }

    @Provides
    BulbDao provideBulbDao(AppDatabase database) {
        return database.bulbDao();
    }

}
