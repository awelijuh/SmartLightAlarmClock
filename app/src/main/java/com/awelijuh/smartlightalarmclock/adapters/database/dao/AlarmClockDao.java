package com.awelijuh.smartlightalarmclock.adapters.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.awelijuh.smartlightalarmclock.adapters.database.domain.AlarmClock;

import java.util.List;
import java.util.Set;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface AlarmClockDao {

    @Insert
    Completable insert(AlarmClock alarmClock);

    @Query("select * from alarmclock")
    Single<List<AlarmClock>> getAll();

    @Query("select * from alarmclock")
    LiveData<List<AlarmClock>> getAllLiveData();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable save(AlarmClock alarmClock);

    @Query("delete from alarmclock where id in (:ids)")
    Completable delete(Set<Long> ids);

}
