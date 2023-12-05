package com.eventico.service;

import com.eventico.model.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;

@Component
@SessionScope
public class LoggedUser {
    private String username;
    private boolean isLogged;
    private boolean isCreator;
    private List<Long> eventsParticipation;
    private List<String> followedUsers;

    public LoggedUser() {
        isLogged = false;
        this.followedUsers = new ArrayList<>();
        this.eventsParticipation = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isLogged() {
        return isLogged;
    }

    public void setLogged(boolean logged) {
        isLogged = logged;
    }

    public boolean isCreator() {
        return isCreator;
    }

    public void setCreator(boolean creator) {
        this.isCreator = creator;
    }

    public void login(User user) {
        this.username = user.getUsername();
        this.isLogged = true;

        this.followedUsers = new ArrayList<>();
        this.eventsParticipation = new ArrayList<>();

        user.getFollowedUsers().stream().forEach((u) -> {
            followedUsers.add(u.getUsername());
        });

        user.getParticipationEvents().stream().forEach((e) -> {
            eventsParticipation.add(e.getId());
        });
    }

    public void logout() {
        this.username = null;
        this.isLogged = false;
        this.isCreator = false;

        this.followedUsers = new ArrayList<>();
        this.eventsParticipation = new ArrayList<>();
    }

    public List<Long> getEventsParticipation() {
        return eventsParticipation;
    }

    public void setEventsParticipation(List<Long> eventsParticipation) {
        this.eventsParticipation = eventsParticipation;
    }

    public List<String> getFollowedUsers() {
        return followedUsers;
    }

    public void setFollowedUsers(List<String> followedUsers) {
        this.followedUsers = followedUsers;
    }
}
