package com.example.opendata;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.text.MessageFormat;

public class MapFragment extends Fragment {

    private MapView mapView;
    private final Data data;

    public MapFragment(Data data){
        super();
        this.data=data;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.map_fragment, container, false);

        TextView country = rootView.findViewById(R.id.tvCounty);
        country.setText(data.getCountryName());
        TextView city = rootView.findViewById(R.id.tvCity);
        city.setText(data.getCity());
        TextView location = rootView.findViewById(R.id.tvLocation);
        location.setText(data.getLocation());
        TextView date = rootView.findViewById(R.id.tvDate);
        date.setText(MessageFormat.format("{0} {1}", data.getLastUpdate().substring(0, 10), data.getLastUpdate().substring(11, 16)));
        TextView pollutant = rootView.findViewById(R.id.tvPollutant);
        pollutant.setText(data.getPollutant());
        TextView value = rootView.findViewById(R.id.tvValue);
        double val =Math.round(data.getValue()*10000.0)/10000.0;
        value.setText(String.format("%sµg/m³", val));

        //initialize map
        mapView = rootView.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                LatLng position = new LatLng(data.getLatitude(), data.getLongitude());
                String markerText = data.getLocation();
                //add marker
                Marker marker  = googleMap.addMarker(new MarkerOptions()
                        .position(position)
                        .title(markerText)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                //zoom to position with level 15
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(position, 15);
                googleMap.animateCamera(cameraUpdate);
            }
        });

        setRetainInstance(true);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

}