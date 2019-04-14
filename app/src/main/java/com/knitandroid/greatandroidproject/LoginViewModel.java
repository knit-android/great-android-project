package com.knitandroid.greatandroidproject;

import android.app.Application;

import com.knitandroid.greatandroidproject.data.User;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import retrofit2.Call;

// are we putting all viewModels together or separate them and if so then should there be sth more in this viewModel?
public class LoginViewModel extends AndroidViewModel {
    private Repository repository;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository();
    }

    /*
        @return {Call<User>} call that' can be later used in loginActivity where it might be processed
    */
    public Call<User> getLoginCall(String username, String password){
        return repository.getLoginCall(username, password);
    }

}
