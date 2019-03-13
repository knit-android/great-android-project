package com.knitandroid.greatandroidproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.http.*;


public class MainActivity extends AppCompatActivity {

    private static final String CLASS_TAG = "Logowanie";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

       Intent intent = new Intent(this, Retrofit_login.class);
       startActivity(intent);


    }
    @Override
    protected void onStart()
    {
        super.onStart();
        Log.d("great-android-project", "uruchomiono onStart()");
    }


}

