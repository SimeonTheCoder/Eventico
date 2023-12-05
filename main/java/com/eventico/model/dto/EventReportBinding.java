package com.eventico.model.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Size;

public class EventReportBinding {
    @Column(nullable = false)
    @Size(min = 10, message = "Report reason should be at least 10 characters")
    private String reason;

    @Column(nullable = false)
    @Size(min = 10, message = "Report description should be at least 10 characters")
    private String description;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
