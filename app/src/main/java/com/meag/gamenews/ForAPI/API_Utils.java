package com.meag.gamenews.ForAPI;


import android.content.Context;

public class API_Utils {

    private API_Utils() {
    }

    public static final String BASE_URL = "http://gamenewsuca.herokuapp.com/";

    public static APIService getAPIService(Context context) {

        return RetrofitClient.getClient(context, BASE_URL).create(APIService.class);
    }
}

