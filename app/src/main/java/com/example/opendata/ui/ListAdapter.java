package com.example.opendata.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.opendata.R;
import com.example.opendata.model.Data;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {

    private final ArrayList<Data> dataArrayList;
    private final Context context;

    public ListAdapter(ArrayList<Data> dataArrayList, Context context) {
        this.dataArrayList = dataArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return dataArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return dataArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ConstraintLayout layoutItem;
        LayoutInflater mInflater = LayoutInflater.from(context);

        if (convertView == null) {
            layoutItem = (ConstraintLayout) mInflater.inflate(R.layout.item_layout, parent, false);
        } else {
            layoutItem = (ConstraintLayout) convertView;
        }

        TextView country = layoutItem.findViewById(R.id.country);
        TextView city = layoutItem.findViewById(R.id.city);
        TextView pollutant = layoutItem.findViewById(R.id.pollutant);
        TextView value = layoutItem.findViewById(R.id.value);

        country.setText(dataArrayList.get(position).getCountryName());
        city.setText(dataArrayList.get(position).getCity());
        pollutant.setText(dataArrayList.get(position).getPollutant());
        value.setText(""+dataArrayList.get(position).getValue());

        return layoutItem;
    }
}
