package com.knitandroid.greatandroidproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.knitandroid.greatandroidproject.data.LastLocation;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainMenuActivity extends AppCompatActivity {
    public double lat =0, lon =0;


    private Repository repository;

    String TAG ="LOC_TAG";

    public LocationGetter locationGetter ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        repository = new Repository();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String cookie=(String) bundle.getString("com.knitandroid.greatandroidproject.ui.COOKIE");

        String[] tabCookie=cookie.split(";",2); //wyciaganie samej wartości jsessionid


        locationGetter=new LocationGetter(this);
        locationGetter.getLocation();
        lat=locationGetter.getLat();
        lon=locationGetter.getLon();

        // TODO pobieranie/wysysłanie lokalizacji dac do petli 


        Call<ResponseBody> locationCall = repository.post_localization(tabCookie[0], lat, lon, 5);

        locationCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.v(TAG, ""+response.code());

                } else {
                    // TODO :: put some logic here
                    Log.v(TAG, "sending localization not successful");
                    Log.v(TAG, ""+response.code());

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.v(TAG, t.getMessage());
            }
        }

        );

        Call<LastLocation[]> locationGet = repository.get_localization(tabCookie[0]);

        locationGet.enqueue(new Callback<LastLocation[]>() {
            @Override
            public void onResponse(Call<LastLocation[]> call, Response<LastLocation[]> response) {
                LastLocation[] lastLocation = response.body();  // LastLocations array


                Log.v(TAG,"get location sucess");
                Log.v(TAG,"location "+response.code());
                Log.v(TAG,""+lastLocation[0].getLatitude()+" "+lastLocation[0].getLongitude());
            }

            @Override
            public void onFailure(Call<LastLocation[]> call, Throwable t) {
                Log.v(TAG,"get location failure");

            }
        });

    }
}
