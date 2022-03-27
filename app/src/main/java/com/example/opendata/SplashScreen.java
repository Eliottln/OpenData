package com.example.opendata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.content.Intent;

import com.example.opendata.AsyncTask.MyAsyncTask;

import java.util.ArrayList;

@SuppressLint("CustomSplashScreen")
public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme();

        if (isNetworkConnected()) {
            Intent main = new Intent(SplashScreen.this, HomeActivity.class);

            SharedPreferences sh = getSharedPreferences("Preferences", MODE_PRIVATE);
            String sort = sh.getString("sort", "measurements_lastupdated");
            boolean increasing = sh.getBoolean("order", false);

            ArrayList<Data> dataArrayList = new ArrayList<>();

            MyAsyncTask task = new MyAsyncTask(output -> {
                main.putExtra("list", dataArrayList);
                startActivity(main);
                finish();
            });
            task.execute(dataArrayList, 0, sort, increasing);

        }else {
            startActivity(new Intent(SplashScreen.this, NoConnectionActivity.class));
            finish();
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    private void setTheme(){
        SharedPreferences sh = getSharedPreferences("Preferences", MODE_PRIVATE);
        String theme = sh.getString("theme", "auto");
        if ("auto".equals(theme)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        } else if ("light".equals(theme)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else if ("dark".equals(theme)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }
}


