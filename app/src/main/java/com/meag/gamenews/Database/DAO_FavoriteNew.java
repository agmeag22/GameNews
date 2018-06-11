package com.meag.gamenews.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;


@Dao
public interface DAO_FavoriteNew {
    @Query("SELECT idnew from favorite")
    List<String> getAllNewsID();

    @Query("DELETE  FROM favorite ")
    void deleteAll();

    @Insert
    void insert(FavoriteNew favorite);

    @Query("DELETE FROM favorite where idnew=:idnew")
    void delete(String idnew);
}

