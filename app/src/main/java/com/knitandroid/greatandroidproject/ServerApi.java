package com.knitandroid.greatandroidproject;

import com.knitandroid.greatandroidproject.data.LastLocation;
import com.knitandroid.greatandroidproject.data.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ServerApi {
    /*
       I'm not sure if we should process User here as we need only cookie
       although in Body of this request there is an user so.
     */
    @GET("api/whoami")
    Call<User> getUser(@Header("Authorization") String authHeader);
    /*
        Post localization on server
     */
    @POST("api/location")
    Call<ResponseBody> postLocalization(
            @Header("Cookie") String auth,
            @Query("latitude") double latitude,
            @Query("longitude") double longitude,
            @Query("accuracy") float accuracy
    );

    /**
     * GET array of LastLocation classes of all users last location
     * @param auth jsessionid
     * @return
     */
    @GET("api/location")
    Call<LastLocation[]> getLocalization(
            @Header("Cookie") String auth
    );
}
