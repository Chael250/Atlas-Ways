package com.app.atlasway.service;

import com.app.atlasway.dao.AttractionDao;
import com.app.atlasway.models.Attraction;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AttractionService {
    private AttractionDao attractionDAO;

    public AttractionService() {
        this.attractionDAO = new AttractionDao();
    }

    public Attraction createAttraction(Attraction attraction) throws SQLException {
        return attractionDAO.save(attraction);
    }

    public Optional<Attraction> getAttractionById(int attractionId) throws SQLException {
        return attractionDAO.findById(attractionId);
    }

    public List<Attraction> getAllAttractions() throws SQLException {
        return attractionDAO.findAll();
    }

    public List<Attraction> getAttractionsByManagerId(int managerId) throws SQLException {
        return attractionDAO.findByManagerId(managerId);
    }

    public List<Attraction> searchAttractions(String keyword) throws SQLException {
        return attractionDAO.search(keyword);
    }

    public Attraction updateAttraction(Attraction attraction) throws SQLException {
        return attractionDAO.save(attraction);
    }

    public boolean deleteAttraction(int attractionId) throws SQLException {
        return attractionDAO.delete(attractionId);
    }
}