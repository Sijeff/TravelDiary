package com.example.demo.Repository;

import com.example.demo.Domain.Journey;
import com.example.demo.Domain.JourneyPart;
import com.example.demo.Domain.Location;
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
    public Journey addJourney(String title, User user) {
        //TODO: Please refactor me! Terrible stuff
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO journeys(title, user_ID) VALUES (?,?) ")) {
            ps.setString(1, title);
            ps.setInt(2, user.getUserID());
            ps.executeUpdate();
            try (PreparedStatement select = conn.prepareStatement("SELECT TOP 1 journeyID, title, user_ID " +
                    "FROM journeys WHERE title = ? AND user_ID = ? ORDER BY journeyID DESC")) {
                select.setString(1, title);
                select.setInt(2, user.getUserID());
                ResultSet rs = select.executeQuery();
                if (rs.next()) {
                    return objJourney(rs);
                }
            }
        } catch (SQLException e) {
            throw new TravelloRepositoryException(e);
        }
        return null;
    }

    @Override
    public void addJourneyPart(String title, String text, Date startDate, Date endDate, int journey_ID, int location_ID) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO journeyParts(title, text, startDate, endDate, journey_ID, location_ID) VALUES (?,?,?,?,?,?) ")) {
            ps.setString(1, title);
            ps.setString(2, text);
            ps.setDate(3, startDate);
            ps.setDate(4, endDate);
            ps.setInt(5, journey_ID);
            ps.setInt(6, location_ID);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new TravelloRepositoryException(e);
        }
    }

    @Override
    public void addLocation(String placeName, String country, float lng, float lat) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO locations(placeName, country, lng, lat) VALUES (?,?,?,?) ")) {
            ps.setString(1, placeName);
            ps.setString(2, country);
            ps.setFloat(3, lng);
            ps.setFloat(4, lat);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new TravelloRepositoryException(e);
        }
    }


    @Override
    public boolean verifyUser(String username, String password) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE name = ? AND password = ?")) {
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
    public boolean verifyLocation(String placeName, String country) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM locations WHERE placeName = ? AND country = ?")) {
            ps.setString(1, placeName);
            ps.setString(2, country);
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

    @Override
    public List<JourneyPart> getJourneyPart(Journey journey) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT journeyPartID, location_ID, startDate, endDate, journey_ID, " +
                     "title, text FROM journeyParts WHERE journey_ID = ? ORDER BY startDate DESC")) {
            ps.setInt(1, journey.getJourneyID());
            ResultSet rs = ps.executeQuery();
            List<JourneyPart> journeyParts = new ArrayList<>();
            while (rs.next()) journeyParts.add(objJourneyPart(rs));
            return journeyParts;
        } catch (SQLException e) {
            throw new TravelloRepositoryException(e);
        }
    }

    @Override
    public Journey getJourney(int journeyID) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT *" +
                     "FROM journeys WHERE journeyID = ?")) {
            ps.setInt(1, journeyID);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new TravelloRepositoryException("The wanted journey could not be found");
            }
            return objJourney(rs);
        } catch (SQLException e) {
            throw new TravelloRepositoryException(e);
        }
    }


    @Override
    public List<Journey> listJourneys() {
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT journeyID, title,user_ID FROM journeys")) {
            List<Journey> journeys = new ArrayList<>();
            while (rs.next()) journeys.add(objJourney(rs));
            return journeys;
        } catch (SQLException e) {
            throw new TravelloRepositoryException(e);
        }
    }

    @Override
    public User getUser(String username) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT *" +
                     "FROM users WHERE name = ?")) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new TravelloRepositoryException("Wrong username.");
            }
            return objUser(rs);
        } catch (SQLException e) {
            throw new TravelloRepositoryException(e);
        }
    }

    @Override
    public User getUserByJourney(Journey journey) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT *" +
                     "FROM users WHERE userID = ?")) {
            ps.setInt(1, journey.getUser_ID());
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new TravelloRepositoryException("No user found");
            }
            return objUser(rs);
        } catch (SQLException e) {
            throw new TravelloRepositoryException(e);
        }
    }

    @Override
    public Journey getJourneyByUserID(int user_ID) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT *" +
                     "FROM journeys WHERE user_ID = ?")) {
            ps.setInt(1, user_ID);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new TravelloRepositoryException("The wanted journey could not be found.");
            }
            return objJourney(rs);
        } catch (SQLException e) {
            throw new TravelloRepositoryException(e);
        }
    }

    @Override
    public Location getLocation(String placeName, String country) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT *" +
                     "FROM locations WHERE placeName = ? AND country = ?")) {
            ps.setString(1, placeName);
            ps.setString(2, country);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new TravelloRepositoryException("Wrong country.");
            }
            return objLocations(rs);
        } catch (SQLException e) {
            throw new TravelloRepositoryException(e);
        }
    }

    private JourneyPart objJourneyPart(ResultSet rs) throws SQLException {
        return new JourneyPart(
                rs.getInt("journeyPartID"),
                rs.getInt("location_ID"),
                rs.getDate("startDate"),
                rs.getDate("endDate"),
                rs.getInt("journey_ID"),
                rs.getString("title"),
                rs.getString("text"));
    }

    private Journey objJourney(ResultSet rs) throws SQLException {
        return new Journey(
                rs.getInt("journeyID"),
                rs.getString("title"),
                rs.getInt("user_ID"));
    }


    private User objUser(ResultSet rs) throws SQLException {
        return new User(
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getDate("birthday"),
                rs.getInt("userID")
        );
    }


    @Override
    public List<Location> getLocations() {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT locationID, lat, lng, placeName, country FROM journeys\n" +
                     "INNER JOIN journeyParts ON journeys.journeyID = journeyParts.journey_ID\n" +
                     "INNER JOIN locations ON locations.locationID = journeyParts.location_ID")) {
            List<Location> locationList = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) locationList.add(objLocations(rs));
            return locationList;
        } catch (SQLException e) {
            throw new TravelloRepositoryException(e);
        }
    }


    private Location objLocations(ResultSet rs) throws SQLException {
        return new Location(
                rs.getInt("locationID"),
                rs.getString("placeName"),
                rs.getString("country"),
                rs.getFloat("lng"),
                rs.getFloat("lat")
        );
    }
}



