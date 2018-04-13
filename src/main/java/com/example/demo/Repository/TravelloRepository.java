package com.example.demo.Repository;

import com.example.demo.Domain.Journey;
import com.example.demo.Domain.JourneyPart;
import com.example.demo.Domain.Location;
import com.example.demo.Domain.User;

import java.time.LocalDate;
import java.util.List;

public interface TravelloRepository {
    void addUser(String name, String email, String password, java.sql.Date birthday, LocalDate regDate);
    Journey addJourney(String title, User user);
    void addJourneyPart(String title, String text, java.sql.Date startDate, java.sql.Date endDate, int journey_ID, int location_ID);
    void addLocation(String placeName, String country, float lng, float lat);
    boolean verifyUser(String username, String password);
    boolean checkUniqueUsername(String username);
    boolean checkDuplicateEmail(String email);
    boolean verifyLocation(String placeName, String country);
    List<JourneyPart> getJourneyPart(Journey journey);
    List<Location> getLocations();
    List<Journey> listJourneys();
    Journey getJourney(int journeyID);
    User getUser(String username);
    User getUserByJourney(Journey journey);
    Journey getJourneyByUserID(int user_ID);
    Location getLocation(String placeName, String country);
}
