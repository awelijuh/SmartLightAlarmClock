package com.awelijuh.smartlightalarmclock.adapters.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.awelijuh.smartlightalarmclock.adapters.database.domain.Bulb;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface BulbDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable save(Bulb bulb);

    @Query("select * from bulb")
    Single<List<Bulb>> getAll();

    @Query("select * from bulb")
    LiveData<List<Bulb>> getAllLiveData();

}
