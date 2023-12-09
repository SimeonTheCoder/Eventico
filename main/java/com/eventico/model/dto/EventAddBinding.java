package com.eventico.model.dto;

import com.eventico.model.enums.EventCategory;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

public class EventAddBinding {
    @Size(min = 3, max = 20, message = "Event title must be between 3 and 20 characters!")
    @Column(nullable = false)
    private String name;

    @Size(min = 3, message = "Event description must be at least 3 characters!")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Min(value = 0, message = "Negative amount impossible!")
    @Column(nullable = false)
    private int cost;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EventCategory category;

    @PastOrPresent(message = "Event start must be in the future!")
    @Column(nullable = false)
    private LocalDateTime start;

    @PastOrPresent(message = "Event end must be in the future!")
    @Column(nullable = false)
    private LocalDateTime end;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String city;

    @NotBlank(message = "Location must not be blank!")
    @Column(nullable = false)
    private String location;

    @NotNull(message = "You must upload an image!")
    MultipartFile filename;

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

    public EventCategory getCategory() {
        return category;
    }

    public void setCategory(EventCategory category) {
        this.category = category;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public MultipartFile getFilename() {
        return filename;
    }

    public void setFilename(MultipartFile filename) {
        this.filename = filename;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
