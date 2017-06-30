package com.asdabholkar.snapkart.external.pojo;

import com.google.gson.annotations.SerializedName;

public class AuthRequest {
    @SerializedName("username")
    String username;

    @SerializedName("password")
    String password;

    public AuthRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
