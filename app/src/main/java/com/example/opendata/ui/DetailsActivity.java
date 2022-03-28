package com.example.opendata.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.opendata.model.Data;
import com.example.opendata.R;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);
        if (savedInstanceState == null) {
            Data data = (Data) getIntent().getSerializableExtra("data");
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new MapFragment(data)).commitNow();
        }
    }

}