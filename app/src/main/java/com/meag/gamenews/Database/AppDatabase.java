package com.meag.gamenews.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;


@Database(entities = {New.class, Player.class, User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {


    //    public abstract FavoriteNewsDao favoriteNewsDao();
    public abstract DAO_New newDAO();

    public abstract DAO_Player playerDAO();

    public abstract DAO_User userDAO();

    private static AppDatabase INSTANCE;


    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "word_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}