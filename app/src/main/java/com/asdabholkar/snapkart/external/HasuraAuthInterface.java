package com.asdabholkar.snapkart.external;

import com.asdabholkar.snapkart.external.pojo.AuthRequest;
import com.asdabholkar.snapkart.external.pojo.AuthResponse;
import com.asdabholkar.snapkart.external.pojo.MessageResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface HasuraAuthInterface {

    @POST(Endpoint.LOGIN)
    Call<AuthResponse> login(@Body AuthRequest request);

    @POST(Endpoint.REGISTER)
    Call<AuthResponse> register(@Body AuthRequest request);

    @GET(Endpoint.LOGOUT)
    Call<MessageResponse> logout();

}
