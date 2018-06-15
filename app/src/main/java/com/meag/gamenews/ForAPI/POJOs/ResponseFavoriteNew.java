package com.meag.gamenews.ForAPI.POJOs;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseFavoriteNew {

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("add")
    @Expose
    private New_API add;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public New_API getAdd() {
        return add;
    }

    public void setAdd(New_API add) {
        this.add = add;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("success" + success).append("add" + add).toString();
    }
}