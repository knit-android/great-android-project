package com.knitandroid.greatandroidproject;

import com.knitandroid.greatandroidproject.data.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface ServerApi {
    /*
       I'm not sure if we should process User here as we need only cookie
       although in Body of this request there is an user so.
     */
    @GET("api/whoami")
    Call<User> getUser(@Header("Authorization") String authHeader);
}
