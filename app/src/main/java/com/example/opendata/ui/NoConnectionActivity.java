package com.example.opendata.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.opendata.AsyncTask.MyAsyncTask;
import com.example.opendata.R;
import com.example.opendata.model.Data;

import java.util.ArrayList;

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
            Intent main = new Intent(NoConnectionActivity.this, HomeActivity.class);

            SharedPreferences sh = getSharedPreferences("Preferences", MODE_PRIVATE);
            String sort = sh.getString("sort", "measurements_lastupdated");
            boolean increasing = sh.getBoolean("order", false);

            ArrayList<Data> dataArrayList = new ArrayList<>();

            MyAsyncTask task = new MyAsyncTask(output -> {
                main.putExtra("list", dataArrayList);
                startActivity(main);
                finish();
            });
            task.execute(dataArrayList, 0, sort, increasing, "");
        }
        else {
            toast.cancel();
            toast.show();
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}