package com.app.atlasway.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Visit {
    private int visitId;
    private int touristId;
    private int attractionId;
    private LocalDate visitDate;
    private boolean visited;
    private Integer rating;
    private String review;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    // Additional fields not stored in DB but for display
    private String attractionName;
    private String touristName;

    // Default constructor
    public Visit() {}

    // Constructor without ID (for new visits)
    public Visit(int touristId, int attractionId, LocalDate visitDate) {
        this.touristId = touristId;
        this.attractionId = attractionId;
        this.visitDate = visitDate;
        this.visited = false;
    }

    // Full constructor
    public Visit(int visitId, int touristId, int attractionId, LocalDate visitDate, boolean visited,
                 Integer rating, String review, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.visitId = visitId;
        this.touristId = touristId;
        this.attractionId = attractionId;
        this.visitDate = visitDate;
        this.visited = visited;
        this.rating = rating;
        this.review = review;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


    public int getVisitId() {
        return visitId;
    }

    public void setVisitId(int visitId) {
        this.visitId = visitId;
    }

    public int getTouristId() {
        return touristId;
    }

    public void setTouristId(int touristId) {
        this.touristId = touristId;
    }

    public int getAttractionId() {
        return attractionId;
    }

    public void setAttractionId(int attractionId) {
        this.attractionId = attractionId;
    }

    public LocalDate getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(LocalDate visitDate) {
        this.visitDate = visitDate;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getAttractionName() {
        return attractionName;
    }

    public void setAttractionName(String attractionName) {
        this.attractionName = attractionName;
    }

    public String getTouristName() {
        return touristName;
    }

    public void setTouristName(String touristName) {
        this.touristName = touristName;
    }

    @Override
    public String toString() {
        return "Visit{" +
                "visitId=" + visitId +
                ", touristId=" + touristId +
                ", attractionId=" + attractionId +
                ", visitDate=" + visitDate +
                ", visited=" + visited +
                ", rating=" + rating +
                '}';
    }
}
