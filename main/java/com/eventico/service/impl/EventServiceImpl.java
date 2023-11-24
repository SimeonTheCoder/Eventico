package com.eventico.service.impl;

import com.eventico.model.dto.EventAddBinding;
import com.eventico.model.entity.Event;
import com.eventico.repo.EventRepository;
import com.eventico.repo.UserRepository;
import com.eventico.service.EventService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;

@Service
public class EventServiceImpl implements EventService {
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    private final LoggedUser loggedUser;

    public EventServiceImpl(UserRepository userRepository, EventRepository eventRepository, LoggedUser loggedUser) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.loggedUser = loggedUser;
    }

    @Override
    public boolean addEvent(EventAddBinding binding) {
        if(binding.getStart().isAfter(binding.getEnd())) return false;

        Event event = new Event();

        event.setName(binding.getName());
        event.setDescription(binding.getDescription());
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
}
