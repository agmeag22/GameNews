package com.meag.gamenews.Database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity(tableName = "favorite")
public class FavoriteNew {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "idnew")
    private String idnew;


    public FavoriteNew(String idnew) {
        this.idnew = idnew;
    }

    public String getIdnew() {
        return idnew;
    }

    public void setIdnew(String idnew) {
        this.idnew = idnew;
    }
}
