package com.app.atlasway.dao;

import com.app.atlasway.models.Attraction;
import com.app.atlasway.util.DatabaseConnection;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AttractionDao {
    public Attraction save(Attraction attraction) throws SQLException {
        String sql;
        if (attraction.getAttractionId() == 0) {
            // Insert new attraction
            sql = "INSERT INTO attractions (manager_id, name, description, location, city, country, " +
                    "category, price, opening_hours, contact_info, image_url) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                    "RETURNING attraction_id, created_at, updated_at";
        } else {
            // Update existing attraction
            sql = "UPDATE attractions SET manager_id = ?, name = ?, description = ?, location = ?, " +
                    "city = ?, country = ?, category = ?, price = ?, opening_hours = ?, " +
                    "contact_info = ?, image_url = ?, updated_at = CURRENT_TIMESTAMP " +
                    "WHERE attraction_id = ? " +
                    "RETURNING created_at, updated_at";
        }

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, attraction.getManagerId());
            stmt.setString(2, attraction.getName());
            stmt.setString(3, attraction.getDescription());
            stmt.setString(4, attraction.getLocation());
            stmt.setString(5, attraction.getCity());
            stmt.setString(6, attraction.getCountry());
            stmt.setString(7, attraction.getCategory());
            stmt.setBigDecimal(8, attraction.getPrice());
            stmt.setString(9, attraction.getOpeningHours());
            stmt.setString(10, attraction.getContactInfo());
            stmt.setString(11, attraction.getImageUrl());

            if (attraction.getAttractionId() != 0) {
                stmt.setInt(12, attraction.getAttractionId());
            }

            rs = stmt.executeQuery();

            if (rs.next()) {
                if (attraction.getAttractionId() == 0) {
                    attraction.setAttractionId(rs.getInt("attraction_id"));
                }
                attraction.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                attraction.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
            }

            return attraction;

        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            DatabaseConnection.close(conn);
        }
    }

    public Optional<Attraction> findById(int attractionId) throws SQLException {
        String sql = "SELECT * FROM attractions WHERE attraction_id = ?";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, attractionId);
            rs = stmt.executeQuery();

            if (rs.next()) {     
                return Optional.of(mapResultSetToAttraction(rs));
            } else {
                return Optional.empty();
            }

        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            DatabaseConnection.close(conn);
        }
    }

    public List<Attraction> findAll() throws SQLException {
        String sql = "SELECT * FROM attractions ORDER BY name";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Attraction> attractions = new ArrayList<>();

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                attractions.add(mapResultSetToAttraction(rs));
            }

            return attractions;

        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            DatabaseConnection.close(conn);
        }
    }

    public List<Attraction> findByManagerId(int managerId) throws SQLException {
        String sql = "SELECT * FROM attractions WHERE manager_id = ? ORDER BY name";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Attraction> attractions = new ArrayList<>();

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, managerId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                attractions.add(mapResultSetToAttraction(rs));
            }

            return attractions;

        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            DatabaseConnection.close(conn);
        }
    }

    public List<Attraction> search(String keyword) throws SQLException {
        String sql = "SELECT * FROM attractions WHERE " +
                "name ILIKE ? OR description ILIKE ? OR location ILIKE ? OR " +
                "city ILIKE ? OR country ILIKE ? OR category ILIKE ? " +
                "ORDER BY name";

        String searchTerm = "%" + keyword + "%";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Attraction> attractions = new ArrayList<>();

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            for (int i = 1; i <= 6; i++) {
                stmt.setString(i, searchTerm);
            }

            rs = stmt.executeQuery();

            while (rs.next()) {
                attractions.add(mapResultSetToAttraction(rs));
            }

            return attractions;

        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            DatabaseConnection.close(conn);
        }
    }

    public boolean delete(int attractionId) throws SQLException {
        String sql = "DELETE FROM attractions WHERE attraction_id = ?";

        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, attractionId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } finally {
            if (stmt != null) stmt.close();
            DatabaseConnection.close(conn);
        }
    }

    private Attraction mapResultSetToAttraction(ResultSet rs) throws SQLException {
        Attraction attraction = new Attraction();
        attraction.setAttractionId(rs.getInt("attraction_id"));
        attraction.setManagerId(rs.getInt("manager_id"));
        attraction.setName(rs.getString("name"));
        attraction.setDescription(rs.getString("description"));
        attraction.setLocation(rs.getString("location"));
        attraction.setCity(rs.getString("city"));
        attraction.setCountry(rs.getString("country"));
        attraction.setCategory(rs.getString("category"));
        attraction.setPrice(rs.getBigDecimal("price"));
        attraction.setOpeningHours(rs.getString("opening_hours"));
        attraction.setContactInfo(rs.getString("contact_info"));
        attraction.setImageUrl(rs.getString("image_url"));
        attraction.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        attraction.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        return attraction;
    }
}
