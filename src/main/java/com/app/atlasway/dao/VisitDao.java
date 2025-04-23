package com.app.atlasway.dao;

import com.app.atlasway.models.Visit;
import com.app.atlasway.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VisitDao {
    public Visit save(Visit visit) throws SQLException {
        String sql;
        if (visit.getVisitId() == 0) {
            // Insert new visit
            sql = "INSERT INTO visits (tourist_id, attraction_id, visit_date, visited) " +
                    "VALUES (?, ?, ?, ?) " +
                    "RETURNING visit_id, created_at, updated_at";
        } else {
            // Update existing visit
            sql = "UPDATE visits SET tourist_id = ?, attraction_id = ?, visit_date = ?, " +
                    "visited = ?, rating = ?, review = ?, updated_at = CURRENT_TIMESTAMP " +
                    "WHERE visit_id = ? " +
                    "RETURNING created_at, updated_at";
        }

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, visit.getTouristId());
            stmt.setInt(2, visit.getAttractionId());
            stmt.setDate(3, java.sql.Date.valueOf(visit.getVisitDate()));
            stmt.setBoolean(4, visit.isVisited());

            if (visit.getVisitId() != 0) {
                if (visit.getRating() != null) {
                    stmt.setInt(5, visit.getRating());
                } else {
                    stmt.setNull(5, Types.INTEGER);
                }
                stmt.setString(6, visit.getReview());
                stmt.setInt(7, visit.getVisitId());
            }

            rs = stmt.executeQuery();

            if (rs.next()) {
                if (visit.getVisitId() == 0) {
                    visit.setVisitId(rs.getInt("visit_id"));
                }
                visit.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                visit.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
            }

            return visit;

        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            DatabaseConnection.close(conn);
        }
    }

    public Optional<Visit> findById(int visitId) throws SQLException {
        String sql = "SELECT v.*, a.name as attraction_name, CONCAT(u.first_name, ' ', u.last_name) as tourist_name " +
                "FROM visits v " +
                "JOIN attractions a ON v.attraction_id = a.attraction_id " +
                "JOIN users u ON v.tourist_id = u.user_id " +
                "WHERE v.visit_id = ?";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, visitId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapResultSetToVisit(rs));
            } else {
                return Optional.empty();
            }

        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            DatabaseConnection.close(   conn);
        }
    }

    public List<Visit> findByTouristId(int touristId) throws SQLException {
        String sql = "SELECT v.*, a.name as attraction_name, CONCAT(u.first_name, ' ', u.last_name) as tourist_name " +
                "FROM visits v " +
                "JOIN attractions a ON v.attraction_id = a.attraction_id " +
                "JOIN users u ON v.tourist_id = u.user_id " +
                "WHERE v.tourist_id = ? " +
                "ORDER BY v.visit_date DESC";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Visit> visits = new ArrayList<>();

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, touristId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                visits.add(mapResultSetToVisit(rs));
            }

            return visits;

        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            DatabaseConnection.close(conn);
        }
    }

    public List<Visit> findByAttractionId(int attractionId) throws SQLException {
        String sql = "SELECT v.*, a.name as attraction_name, CONCAT(u.first_name, ' ', u.last_name) as tourist_name " +
                "FROM visits v " +
                "JOIN attractions a ON v.attraction_id = a.attraction_id " +
                "JOIN users u ON v.tourist_id = u.user_id " +
                "WHERE v.attraction_id = ? " +
                "ORDER BY v.visit_date DESC";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Visit> visits = new ArrayList<>();

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, attractionId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                visits.add(mapResultSetToVisit(rs));
            }

            return visits;

        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            DatabaseConnection.close(conn);
        }
    }

    public List<Visit> findByTouristIdAndVisitStatus(int touristId, boolean visited) throws SQLException {
        String sql = "SELECT v.*, a.name as attraction_name, CONCAT(u.first_name, ' ', u.last_name) as tourist_name " +
                "FROM visits v " +
                "JOIN attractions a ON v.attraction_id = a.attraction_id " +
                "JOIN users u ON v.tourist_id = u.user_id " +
                "WHERE v.tourist_id = ? AND v.visited = ? " +
                "ORDER BY v.visit_date DESC";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Visit> visits = new ArrayList<>();

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, touristId);
            stmt.setBoolean(2, visited);
            rs = stmt.executeQuery();

            while (rs.next()) {
                visits.add(mapResultSetToVisit(rs));
            }

            return visits;

        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            DatabaseConnection.close(conn);
        }
    }

    public boolean markAsVisited(int visitId) throws SQLException {
        String sql = "UPDATE visits SET visited = true, updated_at = CURRENT_TIMESTAMP WHERE visit_id = ?";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, visitId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } finally {
            if (stmt != null) stmt.close();
            DatabaseConnection.close(conn);
        }
    }

    public boolean delete(int visitId) throws SQLException {
        String sql = "DELETE FROM visits WHERE visit_id = ?";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, visitId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } finally {
            if (stmt != null) stmt.close();
            DatabaseConnection.close(conn);
        }
    }

    private Visit mapResultSetToVisit(ResultSet rs) throws SQLException {
        Visit visit = new Visit();
        visit.setVisitId(rs.getInt("visit_id"));
        visit.setTouristId(rs.getInt("tourist_id"));
        visit.setAttractionId(rs.getInt("attraction_id"));
        visit.setVisitDate(rs.getDate("visit_date").toLocalDate());
        visit.setVisited(rs.getBoolean("visited"));

        // These columns may be null
        if (rs.getObject("rating") != null) {
            visit.setRating(rs.getInt("rating"));
        }
        visit.setReview(rs.getString("review"));

        visit.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        visit.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());

        // Additional fields
        if (rs.getObject("attraction_name") != null) {
            visit.setAttractionName(rs.getString("attraction_name"));
        }
        if (rs.getObject("tourist_name") != null) {
            visit.setTouristName(rs.getString("tourist_name"));
        }

        return visit;
    }
}
