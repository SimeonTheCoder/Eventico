package com.eventico.service.impl;

import com.eventico.model.EventViewModel;
import com.eventico.model.dto.BrowseSelectionFilterBinding;
import com.eventico.model.dto.EventAddBinding;
import com.eventico.model.dto.EventDTO;
import com.eventico.model.entity.Event;
import com.eventico.model.entity.User;
import com.eventico.repo.EventRepository;
import com.eventico.repo.UserRepository;
import com.eventico.service.EventService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    private final LoggedUser loggedUser;

    private boolean[] filters = new boolean[9];

    public EventServiceImpl(UserRepository userRepository, EventRepository eventRepository, LoggedUser loggedUser) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.loggedUser = loggedUser;

        this.filters[8] = true;
    }

    @Override
    public boolean addEvent(EventAddBinding binding) {
        if (binding.getStart().isAfter(binding.getEnd())) return false;

        Event event = new Event();

        event.setName(binding.getName());
        event.setDescription(binding.getDescription());
        event.setCost(binding.getCost());
        event.setCategory(binding.getCategory());

        event.setLocation(binding.getLocation());
        event.setStart(binding.getStart());
        event.setEnd(binding.getEnd());

        event.setParticipants(new ArrayList<>());

        try {
            event.setImage(binding.getFilename().getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        event.setAddedBy(userRepository.findByUsername(loggedUser.getUsername()));
        eventRepository.save(event);

        return true;
    }

    @Override
    public EventViewModel getHomeData() {
        List<EventDTO> events = new ArrayList<>();

        eventRepository.findAll().forEach((e) -> {
            events.add(new EventDTO(e));
        });

        return new EventViewModel(events, this.filters);
    }

    @Override
    public boolean enroll(Long id) {
        Event event = eventRepository.findById(id).orElse(null);

        if (event == null) return false;
        if (loggedUser == null) return false;

        User user = userRepository.findByUsername(loggedUser.getUsername());

        if (user.getPoints() < event.getCost()) return false;

        userRepository.updatePointsById(user.getPoints() - event.getCost(), user.getId());

        user.getParticipationEvents().add(event);
        event.getParticipants().add(user);

        userRepository.save(user);
        eventRepository.save(event);

//        userRepository.updateParticipationEventsById(user.getParticipationEvents(), user.getId());
//        eventRepository.save(event);

        return true;
    }

    @Override
    public void filterSearch(BrowseSelectionFilterBinding binding) {
        filters[0] = binding.isArt();
        filters[1] = binding.isCharity();
        filters[2] = binding.isCompetition();
        filters[3] = binding.isGaming();
        filters[4] = binding.isMusic();
        filters[5] = binding.isScience();
        filters[6] = binding.isSport();
        filters[7] = binding.isTheatre();
        filters[8] = binding.isAll();

        boolean positive = false;

        for (int i = 0; i < filters.length; i++) {
            if (filters[i]) positive = true;
        }

        if (!positive) filters[8] = true;
    }

    @Override
    public List<EventDTO> getUserEvents() {
        List<EventDTO> userEvents = new ArrayList<>();

        if (loggedUser == null) return null;
        if (!loggedUser.isCreator()) return null;

        userRepository.findByUsername(loggedUser.getUsername()).getAddedEvents()
                .stream()
                .forEach((e) -> {
                    userEvents.add(new EventDTO(e));
                });

        return userEvents;
    }

    @Override
    public boolean remove(Long id) {
        if (loggedUser == null) return false;
        if (!loggedUser.isCreator()) return false;

        User user = userRepository.findByUsername(loggedUser.getUsername());

        if (eventRepository.findById(id).orElse(null).getAddedBy().getId() != user.getId()) return false;
        eventRepository.deleteById(id);

        return true;
    }

    public boolean[] getFilters() {
        return filters;
    }

    public void setFilters(boolean[] filters) {
        this.filters = filters;
    }

    public void setFilter(int index, boolean value) {
        this.filters[index] = value;
    }
}
