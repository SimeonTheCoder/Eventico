package com.eventico.service.impl;

import com.eventico.repo.EventRepository;
import com.eventico.repo.UserRepository;
import com.eventico.service.EventService;
import org.springframework.stereotype.Service;

@Service
public class EventServiceImpl implements EventService {
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    public EventServiceImpl(UserRepository userRepository, EventRepository eventRepository) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }
}
