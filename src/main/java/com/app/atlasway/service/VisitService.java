package com.app.atlasway.service;

import com.app.atlasway.dao.VisitDao;
import com.app.atlasway.models.Visit;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class VisitService {
    private VisitDao visitDAO;

    public VisitService() {
        this.visitDAO = new VisitDao();
    }

    public Visit createVisit(Visit visit) throws SQLException {
        return visitDAO.save(visit);
    }

    public Optional<Visit> getVisitById(int visitId) throws SQLException {
        return visitDAO.findById(visitId);
    }

    public List<Visit> getVisitsByTouristId(int touristId) throws SQLException {
        return visitDAO.findByTouristId(touristId);
    }

    public List<Visit> getVisitsByAttractionId(int attractionId) throws SQLException {
        return visitDAO.findByAttractionId(attractionId);
    }

    public List<Visit> getPlannedVisitsByTouristId(int touristId) throws SQLException {
        return visitDAO.findByTouristIdAndVisitStatus(touristId, false);
    }

    public List<Visit> getCompletedVisitsByTouristId(int touristId) throws SQLException {
        return visitDAO.findByTouristIdAndVisitStatus(touristId, true);
    }

    public boolean markVisitAsCompleted(int visitId) throws SQLException {
        return visitDAO.markAsVisited(visitId);
    }

    public Visit updateVisit(Visit visit) throws SQLException {
        return visitDAO.save(visit);
    }

    public boolean deleteVisit(int visitId) throws SQLException {
        return visitDAO.delete(visitId);
    }
}