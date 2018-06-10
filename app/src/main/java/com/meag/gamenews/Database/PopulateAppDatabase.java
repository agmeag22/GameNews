package com.meag.gamenews.Database;

import android.app.Application;
import android.os.AsyncTask;

import com.meag.gamenews.ForAPI.APIService;
import com.meag.gamenews.ForAPI.API_Utils;

public class PopulateAppDatabase {

    public static APIService service;
    public static DAO_New new_dao;
    public static DAO_Player player_dao;
    public static DAO_User user_dao;

    public PopulateAppDatabase(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        service = API_Utils.getAPIService();
        new_dao = db.newDAO();
        player_dao = db.playerDAO();
        user_dao = db.userDAO();

    }


}
