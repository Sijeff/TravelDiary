package com.example.demo.Domain;

public class Location {
    private int locationID;
    private String placeName;
    private String country;
    private float lng;
    private float lat;

    public Location(int locationID, String placeName, String country, float lng, float lat) {
        this.locationID = locationID;
        this.placeName = placeName;
        this.country = country;
        this.lng = lng;
        this.lat = lat;
    }


    public int getLocationID() {
        return locationID;
    }

    public String getPlaceName() {
        return placeName;
    }

    public String getCountry() {
        return country;
    }

    public float getLng() {
        return lng;
    }

    public float getLat() {
        return lat;
    }
}
