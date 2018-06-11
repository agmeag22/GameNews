package com.meag.gamenews.Database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


@Entity(tableName = "player")
public class Player {

    @ColumnInfo(name = "avatar")
    private String avatar;
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "_id")
    private String id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "biografy")
    private String biography;

    @ColumnInfo(name = "game")
    private String game;


    public Player(String id, String avatar, String name, String biography, String game) {
        this.id = id;
        this.avatar = avatar;
        this.name = name;
        this.biography = biography;
        this.game = game;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }
}
