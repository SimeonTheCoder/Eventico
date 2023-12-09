package com.eventico.api;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.eventico.model.dto.EventResponseDTO;
import com.eventico.model.entity.Event;
import com.eventico.repo.EventRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class EventAPI {
    private final EventRepository eventRepository;

    public EventAPI(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @GetMapping("/event")
    public ResponseEntity<EventResponseDTO> findById(@RequestParam(value = "id") Long id) {
        Event event = eventRepository.findById(id).orElse(null);

        if(event == null) {
            return ResponseEntity.notFound().build();
        }

        EventResponseDTO eventResponseDTO = new EventResponseDTO(
                event.getId(),
                event.getName(),
                event.getDescription(),
                event.getAddedBy().getUsername(),
                event.getCategory().toString(),
                event.getCost(),
                event.getLocation(),
                event.getStart(),
                event.getEnd()
        );

        eventResponseDTO.add(linkTo(methodOn(EventAPI.class).findById(id)).withSelfRel());

        return new ResponseEntity<>(eventResponseDTO, HttpStatus.OK);
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

    @GetMapping("event/image")
    public ResponseEntity<byte[]> findImageById(@RequestParam(value = "id") Long id) {
        Event event = eventRepository.findById(id).orElse(null);

        if (event == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(event.getImage());
        }
    }
}
