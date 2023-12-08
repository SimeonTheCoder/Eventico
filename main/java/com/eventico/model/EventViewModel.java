package com.eventico.model;

import com.eventico.model.entity.Event;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class EventViewModel {
    private List<Event> events;
    private boolean[] filters;
    private String[] enums = {
            "ART",
            "CHARITY",
            "COMPETITION",
            "GAMING",
            "MUSIC",
            "SCIENCE",
            "SPORT",
            "THEATRE"
    };

    public EventViewModel(List<Event> events, boolean[] filters) {
        this.events = events;
        this.filters = filters;

        events.sort(Comparator.comparing(Event::getStart));
    }

    public EventViewModel() {
        events = new ArrayList<>();
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public boolean[] getFilters() {
        return filters;
    }

    public void setFilters(boolean[] filters) {
        this.filters = filters;
    }

    public String[] getEnums() {
        return enums;
    }

    public void setEnums(String[] enums) {
        this.enums = enums;
    }
}
