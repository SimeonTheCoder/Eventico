package com.eventico.model.dto;

import com.eventico.model.entity.Event;
import org.springframework.web.multipart.MultipartFile;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.util.Base64;

public class EventDTO {
    private Long id;
    private String name;
    private String description;
    private String category;
    private int cost;
    private String location;
    private LocalDateTime start;
    private LocalDateTime end;
    private byte[] image;

    public EventDTO(String name, String description, String location, LocalDateTime start, LocalDateTime end, byte[] image) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.start = start;
        this.end = end;
        this.image = image;
    }

    public EventDTO(Event event) {
        this.id = event.getId();
        this.name = event.getName();
        this.description = event.getDescription();
        this.location = event.getLocation();
        this.start = event.getStart();
        this.end = event.getEnd();
        this.cost = event.getCost();
        this.category = String.valueOf(event.getCategory());
        Base64.Encoder encoder = Base64.getEncoder();
        this.image = encoder.encode(event.getImage());
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
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
}
