package com.meag.gamenews.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;


@Dao
public interface DAO_New {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(New n_new);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Player> player);

    @Query("DELETE FROM new")
    void deleteAll();

    @Query("SELECT * from new WHERE _id=:id")
    New getNewByID(String id);

    @Query("SELECT * from new ORDER BY created_date DESC")
    LiveData<List<New>> getNews();

    @Query("SELECT * FROM new WHERE favorite=1")
    LiveData<List<New>> getFavoritesNews();

    @Query("SELECT * from new WHERE game=:category")
    LiveData<List<New>> getNewsByCategory(String category);


}