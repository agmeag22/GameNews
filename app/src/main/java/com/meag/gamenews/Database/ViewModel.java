package com.meag.gamenews.Database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
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

    public LiveData<List<Player>> getPlayersByCategory(String category) {
        return repository.getPlayersByCategory(category);
    }

    public LiveData<List<New>> getFavoriteNews() {
        return repository.getFavoriteNews();
    }

    public User getUsers() {
        return repository.getUsers();
    }

    public boolean PopulateUserInfo(final String token) {
        return repository.PopulateUserInfo(token);
    }

    public int PopulateNews(final String token) {
        return repository.PopulateNews(token);
    }

    public int PopulatePlayers(final String token) {
        return repository.PopulatePlayers(token);
    }


}