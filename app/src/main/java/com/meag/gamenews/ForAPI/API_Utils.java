package com.meag.gamenews.ForAPI;


public class API_Utils {

    private API_Utils() {
    }

    public static final String BASE_URL = "http://gamenewsuca.herokuapp.com/";

    public static APIService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}

