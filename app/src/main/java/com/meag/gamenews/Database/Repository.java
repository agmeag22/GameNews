package com.meag.gamenews.Database;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.meag.gamenews.ForAPI.APIService;
import com.meag.gamenews.ForAPI.API_Utils;
import com.meag.gamenews.ForAPI.New_API;
import com.meag.gamenews.ForAPI.User_API;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Repository {
    private Application application;
    private DAO_New mdao_new;
    private DAO_Player mdao_player;
    private DAO_User mdao_user;
    private DAO_FavoriteNew mdao_favoritenew;
    private LiveData<List<New>> mAll_News;
    private LiveData<List<New>> mAll_Favorites;
    private APIService apiservice;

    public Repository(Application application) {
        AppDatabase appDatabase = AppDatabase.getDatabase(application);
        mdao_new = appDatabase.newDAO();
        mdao_player = appDatabase.playerDAO();
        mdao_user = appDatabase.userDAO();
        mdao_favoritenew = appDatabase.favoriteNewDAO();
        mAll_News = mdao_new.getNews();
        mAll_Favorites = mdao_new.getFavoritesNews();
        apiservice = API_Utils.getAPIService();

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

    public boolean fillUserInfo(final String string) {

        @SuppressLint("StaticFieldLeak") AsyncTask<String, Void, Boolean> task = new AsyncTask<String, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(String... strings) {
                try {

                    User_API user_api = (User_API) apiservice.getUserDetails(strings[0]).execute().body();
                    if (user_api != null) {
                        User user = new User(user_api.getId(), user_api.getUser(), user_api.getAvatar());
                        mdao_user.insert(user);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
                return true;
            }
        };
        try {
            Boolean resultado = task.execute(string).get();
            return resultado;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean alucivoaqueactualizaraslasnoticias(final String string) {

        @SuppressLint("StaticFieldLeak") AsyncTask<String, Void, Boolean> task = new AsyncTask<String, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(String... strings) {
                try {

                    New_API new_api = (New_API) apiservice.getAllNews(strings[0]).execute().body();
                    if (new_api != null) {
                        New aNew = new New(new_api.getId(), new_api.getTitle(),
                                new_api.getBody(), new_api.getGame(),
                                new_api.getCoverImage(), new_api.getDescription(),
                                new_api.getCreatedDate(), );
                        mdao_user.insert(user);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
                return true;
            }
        };
        try {
            Boolean resultado = task.execute(string).get();
            return resultado;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }
}
