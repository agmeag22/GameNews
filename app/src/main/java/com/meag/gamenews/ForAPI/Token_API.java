package com.meag.gamenews.ForAPI;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Token_API {

    @SerializedName("token")
    @Expose
    private String token;

    public String getToken() {
        return token;
    }

    public String getTokenprocessed() {
        return "Bearer " + token;
    }


    public void setToken(String token) {
        this.token = token;
    }


}
