package com.example.demo.Domain;

public class Journey {
    private int journeyID;
    private String title;
    private int user_ID;

    public Journey(int journeyID, String title, int user_ID) {
        this.journeyID = journeyID;
        this.title = title;
        this.user_ID = user_ID;
    }

    public int getJourneyID() {
        return journeyID;
    }

    public String getTitle() {
        return title;
    }

    public int getUser_ID() {
        return user_ID;
    }
}
