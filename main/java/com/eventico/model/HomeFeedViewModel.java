package com.eventico.model;

import com.eventico.model.dto.EventDTO;

import java.util.List;

public class HomeFeedViewModel {
    private List<EventDTO> foryou;
    private List<EventDTO> participating;

    public HomeFeedViewModel(List<EventDTO> foryou, List<EventDTO> participating) {
        this.foryou = foryou;
        this.participating = participating;
    }

    public List<EventDTO> getForyou() {
        return foryou;
    }

    public void setForyou(List<EventDTO> foryou) {
        this.foryou = foryou;
    }

    public List<EventDTO> getParticipating() {
        return participating;
    }

    public void setParticipating(List<EventDTO> participating) {
        this.participating = participating;
    }
}
