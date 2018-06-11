package com.meag.gamenews.Database;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.meag.gamenews.ForAPI.APIService;
import com.meag.gamenews.ForAPI.API_Utils;
import com.meag.gamenews.ForAPI.FavoriteNew_API;
import com.meag.gamenews.ForAPI.New_API;
import com.meag.gamenews.ForAPI.Player_API;
import com.meag.gamenews.ForAPI.User_API;
import com.meag.gamenews.R;

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

    public boolean PopulateUserInfo(final String token) {

                try {

                    User_API user_api = (User_API) apiservice.getUserDetails(token).execute().body();
                    if (user_api != null) {
                        mdao_user.deleteAll();
                        User user = new User(user_api.getId(), user_api.getUser(), "");
                        mdao_user.insert(user);
                    }
                    List<FavoriteNew_API> favoriteNew_api = user_api.getFavoriteNews();
                    if (favoriteNew_api != null) {
                        for (FavoriteNew_API n : favoriteNew_api) {
                            mdao_favoritenew.insert(new FavoriteNew(n.getId()));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
                return true;
            }


    public int PopulateNews(final String token) {
        List<New_API> list_new_api = null;
        try {
            list_new_api = apiservice.getAllNews(token).execute().body();
            if (list_new_api != null) {
                mdao_new.deleteAll();
                List<String> favoriteNewList = mdao_favoritenew.getAllNewsID();
                for (New_API n : list_new_api) {
                    New aNew = new New(n.getId(), n.getTitle(),
                            n.getBody(), n.getGame(),
                            n.getCoverImage(), n.getDescription(),
                            n.getCreatedDate(), favoriteNewList.contains(n.getId()));
                    mdao_new.insert(aNew);
                }
            } else {
                return R.string.api_error;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return R.string.internal_error;
        }
        return R.string.success;
    }

    public int PopulatePlayers(final String token) {
        List<Player_API> list_player_api = null;
        try {
            list_player_api = apiservice.getAllPlayers(token).execute().body();
            if (list_player_api != null) {
                mdao_player.deleteAll();
                for (Player_API p : list_player_api) {
                    Player player = new Player(p.getId(), p.getAvatar(), p.getName(), p.getBiografia(), p.getGame());
                    mdao_player.insert(player);
                }
            } else {
                return R.string.api_error;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return R.string.internal_error;
        }
        return R.string.success;
    }

}
