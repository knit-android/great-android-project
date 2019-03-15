package com.knitandroid.greatandroidproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.knitandroid.greatandroidproject.data.User;
import com.knitandroid.greatandroidproject.databinding.ActivityMainBinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import androidx.lifecycle.ViewModelProviders;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.knitandroid.greatandroidproject.Repository.HEADER_COOKIE_NAME;


public class MainActivity extends AppCompatActivity implements IMainActivity {
    private static final String TAG = "MAIN_ACTIVITY_TAG";

    public static final String KEY_EXTRA_COOKIE = "com.knitandroid.greatandroidproject.ui.COOKIE";

    private Context appContex;

    private ActivityMainBinding binding;

    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appContex = this;

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setIMainActivity(this);

        viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
    }

    private boolean validateUsername() {
        if (binding.textInputUsername.getEditText().getText().toString().trim().isEmpty()) {
            binding.textInputUsername.setError("username can't be empty");
            return false;
        } else {
            binding.textInputUsername.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        if (binding.textInputPassword.getEditText().getText().toString().trim().isEmpty()) {
            binding.textInputPassword.setError("password can't be empty");
            return false;
        } else {
            binding.textInputPassword.setError(null);
            return true;
        }
    }

    @Override
    public void login() {
        // only one & so we check both inputs and show both errors if necessary
        if (validateUsername() & validatePassword()) {
            // login successful start newActivity
            Call<User> loginCall = viewModel.getLoginCall(binding.textInputUsername.getEditText().getText().toString().trim(), binding.textInputPassword.getEditText().getText().toString().trim());

            loginCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        String cookie = response.headers().get(HEADER_COOKIE_NAME);

                        /* bellow code is mostly for test reasons
                           I'm not sure yet if we should process User in the first place
                         */
                        User user = response.body();
                        Log.v(TAG, "login successful");
                        Log.v(TAG, "cookie = " + cookie);
                        Log.v(TAG, "id = " + user.getId());
                        Log.v(TAG, "username = " + user.getUsername());


                        Intent intent = new Intent(appContex, MainMenuActivity.class);
                        intent.putExtra(KEY_EXTRA_COOKIE, cookie);
                        startActivity(intent);
                    } else {
                        // TODO :: put some logic here
                        Log.v(TAG, "login not successful");
                        Toast.makeText(appContex, "wrong username or password", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    // TODO :: put some logic here
                    Toast.makeText(appContex, "error occured", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
