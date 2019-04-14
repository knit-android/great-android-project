package com.knitandroid.greatandroidproject;

import android.util.Base64;

import com.knitandroid.greatandroidproject.data.LastLocation;
import com.knitandroid.greatandroidproject.data.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// should we make it a singleton?
public class Repository {
    private static final String TAG = "REPOSITORY_TAG";
    public static final String BASE_URL = "http://knitandroidproject.eu-central-1.elasticbeanstalk.com:8081/";
    public static final String HEADER_COOKIE_NAME = "Set-Cookie";
    private Retrofit retrofit;
    private ServerApi serverApi;

    Repository(){
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        serverApi = retrofit.create(ServerApi.class);
    }

    /*
        @return {Call<User>} call that' can be later used in loginActivity where it might be processed
        alternative to method login from below i don't know which way to choose
     */
    public Call<User> getLoginCall(String username, String password){
        String userString = username + ":" + password;

        String authHeader = "Basic " + Base64.encodeToString(userString.getBytes(), Base64.NO_WRAP);
        return serverApi.getUser(authHeader);
    }


    public Call<ResponseBody> post_localization(String cookie, double lat, double lon, float accuracy){

        return serverApi.postLocalization(cookie, lat, lon, accuracy);
    }

    public Call<LastLocation[]> get_localization(String cookie){

        return serverApi.getLocalization(cookie);
    }
}
