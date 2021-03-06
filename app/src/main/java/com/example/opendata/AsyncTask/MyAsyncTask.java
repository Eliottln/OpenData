package com.example.opendata.AsyncTask;

import android.os.AsyncTask;
import androidx.annotation.NonNull;

import com.example.opendata.model.Data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import javax.net.ssl.HttpsURLConnection;

public class MyAsyncTask extends AsyncTask {

    private final Callback callback;

    public MyAsyncTask(Callback callback) {
        this.callback = callback;
    }

    @Override
    protected Object doInBackground(@NonNull Object[] objects) {
        ArrayList<Data> dataArrayList = (ArrayList<Data>) objects[0];
        String message = "";
        boolean decreasing = (boolean) objects[3];
        if (decreasing){
            objects[2]="-"+objects[2];
        }
        String URLString;
        URLString = new StringBuilder().append("https://public.opendatasoft.com/api/records/1.0/search/?dataset=openaq&q=&rows=30&start=")
                .append((Integer) objects[1])
                .append("&sort=")
                .append(objects[2])
                .append("&q=")
                .append(objects[4])
                .toString();

        //get JSON
        try {
            URL url = new URL(URLString);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            if (urlConnection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                InputStreamReader isr = new InputStreamReader(urlConnection.getInputStream());
                BufferedReader input = new BufferedReader(isr);
                StringBuilder stringBuilder = new StringBuilder();
                String temp;
                while ((temp= input.readLine()) != null){
                    stringBuilder.append(temp);
                }
                message = stringBuilder.toString();
                input.close();
            }
            urlConnection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Parse JSON
        try {
            JSONObject jObject = new JSONObject(message);
            JSONArray jArray = jObject.getJSONArray("records");
            for (int i=0; i<jArray.length(); i++) {
                JSONObject record = jArray.getJSONObject(i);
                JSONObject field = record.getJSONObject("fields");

                String countryName = field.optString("country_name_en");
                String pollutant = field.optString("measurements_parameter");
                String location = field.optString("location");
                String city = field.optString("city");
                String lastUpdate = field.optString("measurements_lastupdated");
                String unit = field.optString("measurements_unit");
                double value;
                if (unit.equals("ppm")){
                    value = field.getDouble("measurements_value")*1000;
                }
                else{
                    value = field.getDouble("measurements_value");
                }
                double latitude = (double) field.getJSONArray("coordinates").get(0);
                double longitude = (double) field.getJSONArray("coordinates").get(1);

                dataArrayList.add(new Data(
                        countryName,
                        pollutant,
                        location,
                        city,
                        lastUpdate,
                        value,
                        latitude,
                        longitude));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "Finish";
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        callback.processFinish((String) o);
    }
}
