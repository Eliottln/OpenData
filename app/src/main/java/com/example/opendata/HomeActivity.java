package com.example.opendata;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.opendata.AsyncTask.Callback;
import com.example.opendata.AsyncTask.MyAsyncTask;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private ArrayList<Data> dataArrayList;
    private ListAdapter adapter;
    private int page = 0;
    private String sort = "measurements_lastupdated";
    private boolean increasing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        try {
            Intent splash = getIntent();
            this.dataArrayList = (ArrayList<Data>) splash.getSerializableExtra("list");
            this.adapter = new ListAdapter(dataArrayList, this);
        }catch (Exception e){//n'est pas censé arriver
            this.dataArrayList = new ArrayList<>();
            this.adapter = new ListAdapter(dataArrayList, this);
            executeTask();
        }

        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((adapterView, view, position, l) -> {
            Intent intent = new Intent(this, DetailsActivity.class);
            intent.putExtra("data", dataArrayList.get(position));
            startActivity(intent);
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {}
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem+visibleItemCount == totalItemCount && totalItemCount!=0) {
                    if (isNetworkConnected()) {
                        page += 30;
                        executeTask();
                    }else{
                        Toast toast = Toast.makeText(getApplicationContext(), "No connection", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            }
        });

        adapter.notifyDataSetChanged();
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
            startActivityForResult(intent,100);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode != RESULT_OK) { //settings activity
            SharedPreferences sh = getSharedPreferences("Preferences", MODE_PRIVATE);
            String sort = sh.getString("sort", "measurements_lastupdated");
            boolean order = sh.getBoolean("order", false);

            if (!this.sort.equals(sort) || this.increasing != order){
                this.sort = sort;
                this.increasing = order;
                dataArrayList.clear();
                page=0;
                executeTask();
            }
        }
    }

    public void executeTask(){
        MyAsyncTask task = new MyAsyncTask(new Callback() {
            @Override
            public void processFinish(String output) {
                adapter.notifyDataSetChanged();
            }
        });
        task.execute(dataArrayList, page, sort, increasing);
    }
}