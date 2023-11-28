package com.eventico.controller;

import com.eventico.model.dto.EventAddBinding;
import com.eventico.model.dto.EventDTO;
import com.eventico.repo.EventRepository;
import com.eventico.service.EventService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class EventsController {
    private final EventService eventService;
    private final EventRepository eventRepository;

    public EventsController(EventService eventService, EventRepository eventRepository) {
        this.eventService = eventService;
        this.eventRepository = eventRepository;
    }

    @GetMapping("add-event")
    public ModelAndView addModelPage() {
        return new ModelAndView("add-event");
    }

    @PostMapping("add-event")
    public String addModelForm(@ModelAttribute("eventAddBindingModel") @Valid EventAddBinding eventAddBinding, BindingResult bindingResult) {
        eventService.addEvent(eventAddBinding);
        return "redirect:/home";
    }

    @GetMapping("/event-info/{id}")
    public ModelAndView eventInfoPage(@PathVariable("id") Long id) {
        return new ModelAndView(
                "event-info",
                "event",
                new EventDTO(eventRepository.findById(id).orElse(null))
        );
    }

    @GetMapping("/enroll/{id}")
    public String eventEnroll(@PathVariable("id") Long id) {
        eventService.enroll(id);

        return "redirect:/browse";
    }

    @GetMapping("/remove/{id}")
    public String eventRemove(@PathVariable("id") Long id) {
        eventService.remove(id);

        return "redirect:/manage";
    }
}
