package com.meag.gamenews.Database;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.meag.gamenews.ForAPI.APIService;
import com.meag.gamenews.ForAPI.API_Utils;

import java.util.List;

public class Repository {
    private Application application;
    private DAO_New mdao_new;
    private DAO_Player mdao_player;
    private DAO_User mdao_user;
    private LiveData<List<New>> mAll_News;
    private LiveData<List<New>> mAll_Favorites;
    private APIService apiservice;

    public Repository(Application application) {
        AppDatabase appDatabase = AppDatabase.getDatabase(application);
        mdao_new=appDatabase.newDAO();
        mdao_player=appDatabase.playerDAO();
        mdao_user=appDatabase.userDAO();
        mAll_News =  mdao_new.getNews();
        mAll_Favorites=mdao_new.getFavoritesNews();

    }
    public LiveData<List<New>> getAllNews() {
        return mAll_News;
    }

    public LiveData<List<New>> getNewsByCategory(String category) {
        return mdao_new.getNewsByCategory(category);
    }

    public LiveData<List<Player>> getPlayersByCategory(String category) {
        return mdao_player.getPlayersByCategory(category);
    }

    public LiveData<List<New>> getFavoriteNews() {
        return mAll_Favorites;
    }
    public User getUsers() {
        return mdao_user.getUser();
    }


}
