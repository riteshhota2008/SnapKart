package com.asdabholkar.snapkart.activities.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.asdabholkar.snapkart.R;
import com.asdabholkar.snapkart.activities.home.HomeActivity;
import com.asdabholkar.snapkart.external.Hasura;
import com.asdabholkar.snapkart.external.pojo.AuthRequest;
import com.asdabholkar.snapkart.external.pojo.AuthResponse;
import com.asdabholkar.snapkart.external.pojo.MessageResponse;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity implements View.OnClickListener  {

    EditText firstname, secondName,password;
    Button signInButton, registerButton;

    public static void startActivity(Activity startingActivity) {
        startingActivity.startActivity(new Intent(startingActivity, HomeActivity.class));
        startingActivity.finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Hasura.initialise(this);
        setContentView(R.layout.activity_login);
        firstname = (EditText) findViewById(R.id.firstName);
        secondName= (EditText) findViewById(R.id.secondName);
        password = (EditText) findViewById(R.id.password);
        signInButton = (Button) findViewById(R.id.buttonSignUp);
        registerButton = (Button) findViewById(R.id.buttonSignIn);

        signInButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);

        if (Hasura.getUserSessionId() != null) {
            //ToDoActivity.startActivity(this);
        }

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonSignIn:
                handleLogin();
                break;
            case R.id.buttonSignUp:
                handleRegistration();
                break;
        }

    }
    private void handleLogin() {
//        showProgressIndicator();
        Hasura.auth.login(new AuthRequest(firstname.getText().toString(), password.getText().toString())).enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
//                hideProgressIndicator();
                if (response.isSuccessful()) {
                    Hasura.setSession(response.body());
                    startActivity(new Intent(Login.this, HomeActivity.class));
                } else {
                    try {
                        MessageResponse messageResponse = new Gson().fromJson(response.errorBody().string(), MessageResponse.class);
                        Toast.makeText(Login.this, messageResponse.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
//                hideProgressIndicator();
                Toast.makeText(Login.this, "Something went wrong, please ensure that you have a working internet connection", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void handleRegistration() {
//        showProgressIndicator();
        Hasura.auth.register(new AuthRequest(firstname.getText().toString(), password.getText().toString())).enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
//                hideProgressIndicator();
                if (response.isSuccessful()) {
                    Hasura.setSession(response.body());
                    startActivity(new Intent(Login.this, HomeActivity.class));
                } else {
                    try {
                        MessageResponse messageResponse = new Gson().fromJson(response.errorBody().string(), MessageResponse.class);
                        Toast.makeText(Login.this, messageResponse.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
//                hideProgressIndicator();
                Toast.makeText(Login.this, "Something went wrong, please ensure that you have a working internet connection", Toast.LENGTH_LONG).show();
            }
        });
    }
}
