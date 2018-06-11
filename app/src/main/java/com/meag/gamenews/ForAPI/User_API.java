package com.meag.gamenews.ForAPI;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User_API {

    @SerializedName("favoriteNews")
    @Expose
    private List<FavoriteNew_API> favoriteNews = null;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public List<FavoriteNew_API> getFavoriteNews() {
        return favoriteNews;
    }

    public void setFavoriteNews(List<FavoriteNew_API> favoriteNews) {
        this.favoriteNews = favoriteNews;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("\nfavoriteNews:" + favoriteNews).append("\n").append("id:" + id).append("\n").append("user:" + user).append("\n").append("\n").append("password:" + password).append("\n").append("createdDate:" + createdDate).append("\n").append("v:" + v).append("\n").toString();
    }

}