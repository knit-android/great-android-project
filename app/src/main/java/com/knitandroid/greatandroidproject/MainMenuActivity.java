package com.knitandroid.greatandroidproject;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainMenuActivity extends AppCompatActivity {
    public double lat =0, lon =0;

    private LoginViewModel viewModel;

    String TAG ="LOC_TAG";

    public LocationGetter locationGetter ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Bundle bundle = getIntent().getExtras();
        String Ckie=bundle.getString("KEY_EXTRA_COOKIE");

        viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

        locationGetter=new LocationGetter(this);
        locationGetter.getLocation();
        lat=locationGetter.getLat();
        lon=locationGetter.getLon();
        Log.v("location", lat+",+,"+lon);

        Call<ResponseBody> localizationCall = viewModel.post_localization(Ckie, lat, lon, 5);

        localizationCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                        Log.v(TAG, response.body().toString());

                } else {
                    // TODO :: put some logic here
                    Log.v(TAG, "sending localization not successful");
                    Log.v(TAG, ","+response.code());

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.v(TAG, t.getMessage());
            }
        }

        );

    }
}
