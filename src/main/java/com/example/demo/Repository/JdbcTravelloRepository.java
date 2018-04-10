package com.example.demo.Repository;

import com.example.demo.Domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class JdbcTravelloRepository implements TravelloRepository {

    @SuppressWarnings("SpringJavaAutoInspection")
    @Autowired
    private DataSource dataSource;


    @Override
    public void addUser(String name, String email, String password, Date birthday) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO users(name, email, password, birthday) VALUES (?,?,?,?) ", new String[] {"id"})) {
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.setDate(4, birthday);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new TravelloRepositoryException(e);
        }
    }

    @Override
    public void addJourney(String title, User user) {

    }

    @Override
    public void addJourneyPart(String title, String text, Date startDate, Date endDate) {

    }

    @Override
    public void addLocation(String placeName, String country) {

    }
}
