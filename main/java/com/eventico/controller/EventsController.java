package com.eventico.controller;

import com.eventico.model.dto.EventAddBinding;
import com.eventico.service.EventService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class EventsController {
    private final EventService eventService;

    public EventsController(EventService eventService) {
        this.eventService = eventService;
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
}
