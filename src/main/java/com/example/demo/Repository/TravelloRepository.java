package com.example.demo.Repository;

import com.example.demo.Domain.User;

public interface TravelloRepository {
    void addUser(String name, String email, String password,  java.sql.Date birthday);
    void addJourney(String title, User user);
    void addJourneyPart(String title, String text, java.sql.Date startDate, java.sql.Date endDate, int journey_ID);
    void addLocation(String placeName, String country, int journeyParts_id);
}
