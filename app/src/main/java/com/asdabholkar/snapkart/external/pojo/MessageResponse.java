package com.asdabholkar.snapkart.external.pojo;

import com.google.gson.annotations.SerializedName;

public class MessageResponse {
    @SerializedName("message")
    String message;

    public String getMessage() {
        return message;
    }
}
