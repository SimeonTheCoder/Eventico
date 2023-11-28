package com.eventico.api;

import com.eventico.model.dto.EventResponseDTO;
import com.eventico.model.entity.Event;
import com.eventico.repo.EventRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class EventAPI {
    private final EventRepository eventRepository;

    public EventAPI(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @GetMapping("/event/{id}")
    public ResponseEntity<EventResponseDTO> findById(@PathVariable(value = "id") Long id) {
        Event event = eventRepository.findById(id).orElse(null);

        if (event == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(new EventResponseDTO(event));
        }
    }

    @GetMapping("events/all")
    public ResponseEntity<List<EventResponseDTO>> getAll() {
        List<Event> events = eventRepository.findAll();
        List<EventResponseDTO> responses = new ArrayList<>();

        events.stream().forEach((e) -> {
            responses.add(new EventResponseDTO(e));
        });

        if (events.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(responses);
        }
    }

    @GetMapping("event/image/{id}")
    public ResponseEntity<byte[]> findImageById(@PathVariable(value = "id") Long id) {
        Event event = eventRepository.findById(id).orElse(null);

        if (event == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(event.getImage());
        }
    }
}
