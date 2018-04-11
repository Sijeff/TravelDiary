package com.example.demo.Domain;

public class Location {
    private int locationID;
    private String placeName;
    private String country;
    private int journeyPart_ID;

    public Location(int locationID, String placeName, String country, int journeyPart_ID) {
        this.locationID = locationID;
        this.placeName = placeName;
        this.country = country;
        this.journeyPart_ID = journeyPart_ID;
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

    public int getJourneyPart_ID() {
        return journeyPart_ID;
    }
}
