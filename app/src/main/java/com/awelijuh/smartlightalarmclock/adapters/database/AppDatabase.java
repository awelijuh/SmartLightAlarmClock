package com.awelijuh.smartlightalarmclock.adapters.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.awelijuh.smartlightalarmclock.adapters.database.dao.AlarmClockDao;
import com.awelijuh.smartlightalarmclock.adapters.database.dao.BulbDao;
import com.awelijuh.smartlightalarmclock.adapters.database.dao.ScriptDao;
import com.awelijuh.smartlightalarmclock.adapters.database.domain.AlarmClock;
import com.awelijuh.smartlightalarmclock.adapters.database.domain.Bulb;

@Database(version = 1, entities = {AlarmClock.class, Bulb.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract AlarmClockDao alarmClockDao();

    public abstract BulbDao bulbDao();

    public abstract ScriptDao scriptDao();

}
