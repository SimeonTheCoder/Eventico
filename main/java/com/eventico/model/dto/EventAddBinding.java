package com.eventico.model.dto;

import com.eventico.model.enums.EventCategory;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

public class EventAddBinding {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EventCategory category;

    @Column(nullable = false)
    private LocalDateTime start;

    @Column(nullable = false)
    private LocalDateTime end;

    @Column(nullable = false)
    private String location;

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
}
