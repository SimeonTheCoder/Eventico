package com.eventico.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name = "reports")
public class Report extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "reported_event_id", nullable = false)
    private Event reportedEvent;

    @ManyToOne
    @JoinColumn(name = "reported_by_id")
    private User reportedBy;

    @Column(nullable = false)
    private LocalDateTime reportTime;

    @Column(nullable = false)
    @Size(min = 10, message = "Report reason should be at least 10 characters")
    private String reason;

    @Column(nullable = false)
    @Size(min = 10, message = "Report description should be at least 10 characters")
    private String description;

    public User getReportedBy() {
        return reportedBy;
    }

    public void setReportedBy(User reportedBy) {
        this.reportedBy = reportedBy;
    }

    public Event getReportedEvent() {
        return reportedEvent;
    }

    public void setReportedEvent(Event reportedEvent) {
        this.reportedEvent = reportedEvent;
    }

    public LocalDateTime getReportTime() {
        return reportTime;
    }

    public void setReportTime(LocalDateTime reportTime) {
        this.reportTime = reportTime;
    }

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
