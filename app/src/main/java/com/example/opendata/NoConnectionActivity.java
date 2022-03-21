package com.example.opendata;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class NoConnectionActivity extends AppCompatActivity {

    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_connection);

        toast = Toast.makeText(getApplicationContext(), "No connection", Toast.LENGTH_SHORT);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void reload(View view){
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        if (isNetworkConnected()){
            startActivity(new Intent(NoConnectionActivity.this, HomeActivity.class));
            finish();
        }
        else {
            toast.cancel();
            toast.show();
        }
        //progressBar.setVisibility(View.INVISIBLE);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}