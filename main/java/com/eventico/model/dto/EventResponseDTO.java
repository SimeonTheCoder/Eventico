package com.eventico.model.dto;

import com.eventico.model.entity.Event;

import java.time.LocalDateTime;
import java.util.Base64;

public class EventResponseDTO {
    private Long id;
    private String name;
    private String description;
    private String addedBy;
    private String category;
    private int cost;
    private String location;
    private LocalDateTime start;
    private LocalDateTime end;

    public EventResponseDTO(Event event) {
        this.id = event.getId();
        this.name = event.getName();
        this.description = event.getDescription();
        this.location = event.getLocation();
        this.start = event.getStart();
        this.end = event.getEnd();
        this.cost = event.getCost();
        this.category = String.valueOf(event.getCategory());
        this.addedBy = event.getAddedBy().getUsername();
        Base64.Encoder encoder = Base64.getEncoder();
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

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }
}
