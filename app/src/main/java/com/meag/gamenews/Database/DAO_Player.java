package com.meag.gamenews.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface DAO_Player {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Player player);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Player> players);

    @Query("DELETE FROM player")
    void deleteAll();

    @Query("SELECT * from player WHERE _id=:id")
    Player getPlayerByID(String id);

    @Query("SELECT * from player WHERE game=:category")
    LiveData<List<Player>> getPlayersByCategory(String category);
}