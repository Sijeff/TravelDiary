package com.example.demo.Repository;

import com.example.demo.Domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTravelloRepository implements TravelloRepository {

    @SuppressWarnings("SpringJavaAutoInspection")
    @Autowired
    private DataSource dataSource;


    @Override
    public void addUser(String name, String email, String password, Date birthday, LocalDate regDate) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO users(name, email, password, birthday,registrationDate) VALUES (?,?,?,?,?) ")) {
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.setDate(4, birthday);
            ps.setDate(5, Date.valueOf(regDate));
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new TravelloRepositoryException(e);
        }
    }

    @Override
    public void addJourney(String title, User user) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO journeys(title, user_ID) VALUES (?,?) ")) {
            ps.setString(1, title);
            ps.setInt(2, user.getUserID());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new TravelloRepositoryException(e);
        }
    }

    @Override
    public void addJourneyPart(String title, String text, Date startDate, Date endDate, int journey_ID) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO users(title, text, startDate, endDate, journey_ID) VALUES (?,?,?,?,?) ")) {
            ps.setString(1, title);
            ps.setString(2, text);
            ps.setDate(3, startDate);
            ps.setDate(4, endDate);
            ps.setInt(5, journey_ID);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new TravelloRepositoryException(e);
        }
    }

    @Override
    public void addLocation(String placeName, String country, int journeyParts_id) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO journeys(placeName, country, journeyParts_id) VALUES (?,?,?) ")) {
            ps.setString(1, placeName);
            ps.setString(2, country);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new TravelloRepositoryException(e);
        }
    }


    @Override
    public boolean verifyUser(String username, String password) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?")) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new TravelloRepositoryException(e);
        }
        return false;
    }


    @Override
    public boolean checkUniqueUsername(String username) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT name FROM USERS WHERE name = ?")) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next())
                    return false;
            }

        } catch (SQLException e) {
            throw new TravelloRepositoryException(e);
        }
        return true;
    }

    @Override
    public boolean checkDuplicateEmail(String email) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT email FROM USERS WHERE email = ?")) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next())
                    return true;
            }

        } catch (SQLException e) {
            throw new TravelloRepositoryException(e);
        }
        return false;

    }
}



