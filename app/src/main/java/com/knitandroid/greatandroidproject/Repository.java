package com.knitandroid.greatandroidproject;

import android.util.Base64;
import android.util.Log;

import com.knitandroid.greatandroidproject.data.User;

import androidx.annotation.Nullable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// should we make it a singleton?
public class Repository {
    private static final String TAG = "REPOSITORY_TAG";
    public static final String BASE_URL = "http://knitandroidproject.eu-central-1.elasticbeanstalk.com:8081/";
    public static final String HEADER_COOKIE_NAME = "Set-Cookie";
    private Retrofit retrofit;

    Repository(){
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /*
        @return {Call<User>} call that' can be later used in loginActivity where it might be processed
        alternative to method login from below i don't know which way to choose
     */
    public Call<User> getLoginCall(String username, String password){
        ServerApi serverApi = retrofit.create(ServerApi.class);
        String userString = username + ":" + password;
        String authHeader = "Basic " + Base64.encodeToString(userString.getBytes(), Base64.NO_WRAP);
        return serverApi.getUser(authHeader);
    }

    /*
        @return {String} cookies for current session
        if login was not successful return null TODO:: <-- it's just a quick solution this might be changed for sth better

        in this method it is not easily possible to return cookie because:
        cookie is created in onResponse,
        cookie would have to be a field in Repository which is not a best practice that's why I think about
        returning implemented login call which can be then accessed in login activity is it better?
     */
    @Nullable
    public String login(String username, String password){
        ServerApi serverApi = retrofit.create(ServerApi.class);
        String userString = username + ":" + password;
        String authHeader = "Basic " + Base64.encodeToString(userString.getBytes(), Base64.NO_WRAP);

        Call<User> call = serverApi.getUser(authHeader);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    User user = response.body();
                    String cookie = response.headers().get(HEADER_COOKIE_NAME);
                    Log.v(TAG, "login successful");
                    Log.v(TAG, "cookie = " + cookie);
                    Log.v(TAG, "id = " + user.getId());
                    Log.v(TAG, "username = " + user.getUsername());
                } else {
                    Log.v(TAG, "login not successful");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                    // TODO :: implement failure
            }
        });
        // TODO:: add class field, assign it in onResponse and return
        return null;
    }

}
