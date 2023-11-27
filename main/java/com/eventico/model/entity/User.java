package com.eventico.model.entity;

import com.eventico.model.enums.UserRoles;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends BaseEntity{
    @Column(unique = true, nullable = false)
    @Size(min = 3, max = 20, message = "Username length must be between 3 and 20 characters!")
    private String username;

    @Column(nullable = false)
    private String password;

    @Email
    @Column(unique = true, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRoles role;

    @Column(nullable = false)
    private int points;

    @OneToMany(mappedBy = "addedBy")
    private List<Event> addedEvents;

    @ManyToMany(mappedBy = "participants", fetch = FetchType.EAGER)
    private List<Event> participationEvents;

    public User() {
        participationEvents = new ArrayList<>();
        addedEvents = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRoles getRole() {
        return role;
    }

    public void setRole(UserRoles role) {
        this.role = role;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public List<Event> getAddedEvents() {
        return addedEvents;
    }

    public void setAddedEvents(List<Event> addedEvents) {
        this.addedEvents = addedEvents;
    }

    public List<Event> getParticipationEvents() {
        return participationEvents;
    }

    public void setParticipationEvents(List<Event> participationEvents) {
        this.participationEvents = participationEvents;
    }
}
