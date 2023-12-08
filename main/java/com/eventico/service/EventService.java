package com.eventico.service;

import com.eventico.model.EventViewModel;
import com.eventico.model.HomeFeedViewModel;
import com.eventico.model.dto.BrowseSelectionFilterBinding;
import com.eventico.model.dto.EventAddBinding;
import com.eventico.model.entity.Event;

import java.util.List;

public interface EventService {
    boolean addEvent(EventAddBinding binding);

    EventViewModel getHomeData();

    boolean enroll(Long id);

    void filterSearch(BrowseSelectionFilterBinding binding);

    List<Event> getUserEvents();

    boolean remove(Long id);

    HomeFeedViewModel getEventsForUser(String username);

    Event getEvent(Long id);

    List<Event> getAll();
}
