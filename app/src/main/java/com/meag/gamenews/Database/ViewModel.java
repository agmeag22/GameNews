package com.meag.gamenews.Database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import java.util.List;


public class ViewModel extends AndroidViewModel {
    Repository repository;


    public ViewModel(@NonNull Application application) {
        super(application);
        this.repository = new Repository(application);
    }

    public LiveData<List<New>> getAllNews() {
        return repository.getAllNews();
    }

    public LiveData<List<New>> getNewsByCategory(String category) {
        return repository.getNewsByCategory(category);
    }

    public LiveData<List<New>> getNewsByTitleLike(String title) {
        return repository.getNewsByTitleLike(title);
    }

    public LiveData<List<Player>> getPlayersByCategory(String category) {
        return repository.getPlayersByCategory(category);
    }

    public LiveData<List<New>> getFavoriteNews() {
        return repository.getFavoriteNews();
    }

    public User getUsers() {
        return repository.getUsers();
    }

    public boolean PopulateUserInfo(final String token, SharedPreferences sp) {
        return repository.PopulateUserInfo(token, sp);
    }

    public LiveData<List<String>> getCategories() {
        return repository.getCategories();
    }

    public int PopulateNews(final String token) {
        return repository.PopulateNews(token);
    }

    public int PopulatePlayers(final String token) {
        return repository.PopulatePlayers(token);
    }

    public boolean Setfavorite(String token, String iduser, String idnew) {
        return repository.setfavorite(token, iduser, idnew);
    }

    public boolean Unsetfavorite(String token, String iduser, String idnew) {
        return repository.unsetfavorite(token, iduser, idnew);
    }
}