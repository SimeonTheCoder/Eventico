package com.eventico.service;

import com.eventico.model.EventViewModel;
import com.eventico.model.dto.BrowseSelectionFilterBinding;
import com.eventico.model.dto.EventAddBinding;
import com.eventico.model.dto.EventDTO;

import java.util.List;

public interface EventService {
    public boolean addEvent(EventAddBinding binding);

    public EventViewModel getHomeData();

    public boolean enroll(Long id);

    public void filterSearch(BrowseSelectionFilterBinding binding);

    List<EventDTO> getUserEvents();

    boolean remove( Long id);
}
