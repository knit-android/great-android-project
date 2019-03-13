package com.knitandroid.greatandroidproject;

import android.content.Context;
import android.os.Bundle;

import com.knitandroid.greatandroidproject.databinding.ActivityMainBinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

public class MainActivity extends AppCompatActivity {
    Context appContex;
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appContex=this;
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }
}
