package com.example.demo.Domain;

import java.sql.Date;

public class JourneyPart {
    private int journeyPartID;
    private int location_ID;
    private Date startDate;
    private Date endDate;
    private int journey_ID;
    private String title;
    private String text;

    public JourneyPart(int journeyPartID, int location_ID, Date startDate, Date endDate, int journey_ID, String title, String text) {
        this.journeyPartID = journeyPartID;
        this.location_ID = location_ID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.journey_ID = journey_ID;
        this.title = title;
        this.text = text;
    }

    public int getJourneyPartID() {
        return journeyPartID;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public int getJourney_ID() {
        return journey_ID;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public int getLocation_ID() {
        return location_ID;
    }
}
