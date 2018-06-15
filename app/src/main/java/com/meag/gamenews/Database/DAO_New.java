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

    @Query("SELECT * from new WHERE game=:category ORDER BY created_date DESC")
    LiveData<List<New>> getNewsByCategory(String category);

    @Query("SELECT game from new GROUP BY game ORDER BY created_date DESC")
    LiveData<List<String>> getCategories();

    @Query("UPDATE new SET favorite=1 WHERE _id=:id")
    void setFavorite(String id);

    @Query("UPDATE new SET favorite=0 WHERE _id=:id")
    void unsetFavorite(String id);

}
