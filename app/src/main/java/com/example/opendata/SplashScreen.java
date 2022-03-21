package com.example.opendata;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.net.ConnectivityManager;
import android.os.Bundle;

import android.content.Intent;
import android.os.Handler;
import android.view.WindowManager;

@SuppressLint("CustomSplashScreen")
public class SplashScreen extends AppCompatActivity {

    public static boolean PARSE_END = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isNetworkConnected())
            startActivity(new Intent(SplashScreen.this, HomeActivity.class));
        else
            startActivity(new Intent(SplashScreen.this, NoConnectionActivity.class));
        finish();
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}


