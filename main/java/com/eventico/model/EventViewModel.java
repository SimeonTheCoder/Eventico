package com.eventico.model;

import com.eventico.model.dto.EventDTO;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class EventViewModel {
    private List<EventDTO> events;
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

    public EventViewModel(List<EventDTO> events, boolean[] filters) {
        this.events = events;
        this.filters = filters;

        events.sort(Comparator.comparing(EventDTO::getStart));
    }

    public EventViewModel() {
        events = new ArrayList<>();
    }

    public List<EventDTO> getEvents() {
        return events;
    }

    public void setEvents(List<EventDTO> events) {
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
