package com.knitandroid.greatandroidproject.data;

import com.google.gson.annotations.SerializedName;

public class User {

    private String username;

    @SerializedName("userId")
    private int id;

    public User(String username, int id) {
        this.username = username;
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
