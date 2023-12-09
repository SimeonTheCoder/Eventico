package com.eventico.model.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "countries")
public class Country extends BaseEntity{
    @Column(unique = true, nullable = false)
    private String name;

    private String flag;

    @OneToMany(mappedBy = "country", fetch = FetchType.EAGER)
    private List<City> cities;

    @OneToMany(mappedBy = "country", fetch = FetchType.EAGER)
    private List<User> users;

    @OneToMany(mappedBy = "country", fetch = FetchType.EAGER)
    private List<Event> events;

    public Country() {
        cities = new ArrayList<>();
        users = new ArrayList<>();
        events = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
