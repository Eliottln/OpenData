package com.example.opendata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<Data> dataArrayList;
    private ListAdapter adapter;
    private int page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        this.dataArrayList = new ArrayList<>();
        this.adapter = new ListAdapter(dataArrayList, this);

        try {
            SharedPreferences sh = getSharedPreferences("language", MODE_PRIVATE);

            String s1 = sh.getString("language", "");

            // Setting the fetched data
            // in the EditTexts
            TextView t = findViewById(R.id.headerTitle);
            t.setText(s1);
        }
        catch (Exception e){
            e.printStackTrace();
        }


        listView = findViewById(R.id.listView);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener((adapterView, view, position, l) -> {
            Intent intent = new Intent(this, DetailsActivity.class);
            intent.putExtra("data", dataArrayList.get(position));
            startActivity(intent);
        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem+visibleItemCount == totalItemCount && totalItemCount!=0)
                {
                    if (isNetworkConnected()) {
                        page += 30;
                        new MyAsyncTask().execute(dataArrayList, adapter, page);
                        TextView test = findViewById(R.id.recordsQuantity);
                        String tmp = Integer.toString((page + 30));
                        test.setText(tmp);
                    }else{
                        Toast toast = Toast.makeText(getApplicationContext(), "No connection", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            }
        });

        new MyAsyncTask().execute(dataArrayList, adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.preferences) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}