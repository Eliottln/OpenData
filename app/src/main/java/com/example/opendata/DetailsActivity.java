package com.example.opendata;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class DetailsActivity extends AppCompatActivity {
    private Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);
        if (savedInstanceState == null) {
            data = (Data) getIntent().getSerializableExtra("data");
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new MapFragment(data)).commitNow();
        }
    }
}