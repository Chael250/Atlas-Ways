package com.app.atlasway.models;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Attraction {
    private int attractionId;
    private int managerId;
    private String name;
    private String description;
    private String location;
    private String city;
    private String country;
    private String category;
    private BigDecimal price;
    private String openingHours;
    private String contactInfo;
    private String imageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Default constructor
    public Attraction() {}

    // Constructor without ID (for new attractions)
    public Attraction(int managerId, String name, String description, String location, String city,
                      String country, String category, BigDecimal price, String openingHours,
                      String contactInfo, String imageUrl) {
        this.managerId = managerId;
        this.name = name;
        this.description = description;
        this.location = location;
        this.city = city;
        this.country = country;
        this.category = category;
        this.price = price;
        this.openingHours = openingHours;
        this.contactInfo = contactInfo;
        this.imageUrl = imageUrl;
    }

    // Full constructor
    public Attraction(int attractionId, int managerId, String name, String description, String location,
                      String city, String country, String category, BigDecimal price, String openingHours,
                      String contactInfo, String imageUrl, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.attractionId = attractionId;
        this.managerId = managerId;
        this.name = name;
        this.description = description;
        this.location = location;
        this.city = city;
        this.country = country;
        this.category = category;
        this.price = price;
        this.openingHours = openingHours;
        this.contactInfo = contactInfo;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getAttractionId() {
        return attractionId;
    }

    public void setAttractionId(int attractionId) {
        this.attractionId = attractionId;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    @Override
    public String toString() {
        return "Attraction{" +
                "attractionId=" + attractionId +
                ", managerId=" + managerId +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                '}';
    }
}
