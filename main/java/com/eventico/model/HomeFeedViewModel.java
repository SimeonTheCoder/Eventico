package com.eventico.model;

import com.eventico.model.entity.Event;

import java.util.List;

public class HomeFeedViewModel {
    private List<Event> foryou;
    private List<Event> participating;

    public HomeFeedViewModel(List<Event> foryou, List<Event> participating) {
        this.foryou = foryou;
        this.participating = participating;
    }

    public List<Event> getForyou() {
        return foryou;
    }

    public void setForyou(List<Event> foryou) {
        this.foryou = foryou;
    }

    public List<Event> getParticipating() {
        return participating;
    }

    public void setParticipating(List<Event> participating) {
        this.participating = participating;
    }
}
