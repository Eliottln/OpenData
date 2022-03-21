package com.example.opendata;

import java.io.Serializable;

public class Data implements Serializable {

    private String countryName;
    private String pollutant;
    private String location;
    private String city;
    private String lastUpdate;
    private double value;
    private double latitude;
    private double longitude;

    public Data(String countryName, String pollutant, String location, String city, String lastUpdate, double value, double latitude, double longitude) {
        this.countryName = countryName;
        this.location = location;
        this.city = city;
        this.pollutant = pollutant;
        this.lastUpdate = lastUpdate;
        this.value = value;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getLocation() {
        return location;
    }

    public String getCity() {
        return city;
    }

    public String getPollutant() {
        return pollutant;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public double getValue() {
        return value;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
