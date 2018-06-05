package com.meag.gamenews.ForAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIService {


    @POST("/login")
    @FormUrlEncoded
    Call<Token> login(@Field("user") String user, @Field("password") String password);


    //Obtaining list of Users
    @GET("/users")
    Call<List<User>> getAllUsers(@Header("Authorization") String authHeader);

    //Adding a User
    @POST("/users")
    @FormUrlEncoded
    Call<User> addUser(@Header("Authorization") String authHeader, @Field("user") String user,
                       @Field("avatar") String avatar, @Field("password") String password);

    //Update a password from an user
    @PUT("/users/{id}")
    @FormUrlEncoded
    Call<User> editUser(@Header("Authorization") String authHeader, @Path("id") String id, @Field("password") String password);

    //Obtaining user by ID
    @GET("/users/{id}")
    @FormUrlEncoded
    Call<User> getUserByID(@Header("Authorization") String authHeader, @Path("id") String id);

    //Deleting user by ID
    @DELETE("/users/{id}")
    @FormUrlEncoded
    Call<User> deleteUserByID(@Header("Authorization") String authHeader, @Path("id") String id);

    //Add favorite new to an User
    @POST("/users/{id}/fav")
    @FormUrlEncoded
    Call<User> addUserFav(@Header("Authorization") String authHeader, @Path("id") String id, @Field("new") String n_new);

    //Delete a favorite new from an user
    @DELETE("/users/{id}/fav")
    @FormUrlEncoded
    Call<User> deleteUserFav(@Header("Authorization") String authHeader, @Path("id") String id, @Field("new") String n_new);

    //******** ADMINISTRACION DE NOTICIAS ********//

    //Obtaining all news
    @GET("/news")
    Call<List<New>> getAllNews(@Header("Authorization") String authHeader);

    //Obtainining new's category
    @GET("/news/type/list")
    Call<List<String>> getNewsCategory(@Header("Authorization") String authHeader);

    //Obtaining a new by category
    @GET("/news/type/{category}")
    Call<List<New>> getNewsByCategory(@Header("Authorization") String authHeader, @Path("category") String category);

    //Add a new "new"
    @POST("/news")
    @FormUrlEncoded
    Call<New> addNew(@Header("Authorization") String authHeader, @Path("title") String title, @Path("description")
            String description, @Path("coverImage") String coverImage, @Path("body") String body, @Path("game") String category);

    //Obtaining new by id
    @GET("/news/{id}")
    Call<New> getNewByID(@Header("Authorization") String authHeader, @Path("id") String id);

    //******** ADMINISTRACION DE PLAYERS ********//

    //Obtaining all players
    @GET("/players")
    Call<List<Player>> getAllPlayers(@Header("Authorization") String authHeader);

    //Obtaining list of games by players
    @GET("/players/type/list")
    Call<List<String>> getPlayersCategory(@Header("Authorization") String authHeader);

    //Add a new player
    @GET("/players")
    @FormUrlEncoded
    Call<List<String>> getPlayersByCategory(@Header("Authorization") String authHeader, @Field("name") String name, @Field("biografia") String biografia, @Field("avatar") String avatar, @Field("game") String category);

    //Obtain player by Id
    @GET("/players/{id}")
    Call<Player> getPlayerByID(@Header("Authorization") String authHeader, @Path("id") String id);

}
