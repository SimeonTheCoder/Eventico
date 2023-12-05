package com.eventico.controller;

import com.eventico.exceptions.AccessDeniedException;
import com.eventico.exceptions.EventNotFoundException;
import com.eventico.model.dto.EventAddBinding;
import com.eventico.model.dto.EventDTO;
import com.eventico.model.dto.EventReportBinding;
import com.eventico.model.entity.Event;
import com.eventico.repo.EventRepository;
import com.eventico.service.EventService;
import com.eventico.service.LoggedUser;
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
    private final LoggedUser loggedUser;

    public EventsController(EventService eventService, LoggedUser loggedUser) {
        this.eventService = eventService;
        this.loggedUser = loggedUser;
    }

    @GetMapping("add-event")
    public ModelAndView addModelPage() {
        if(!loggedUser.isLogged()) throw new AccessDeniedException();
        if(!loggedUser.isCreator()) throw new AccessDeniedException();

        return new ModelAndView("add-event");
    }

    @PostMapping("add-event")
    public String addModelForm(@ModelAttribute("eventAddBindingModel") @Valid EventAddBinding eventAddBinding, BindingResult bindingResult) {
        if(!loggedUser.isLogged()) throw new AccessDeniedException();
        if(!loggedUser.isCreator()) throw new AccessDeniedException();

        eventService.addEvent(eventAddBinding);
        return "redirect:/home";
    }

    @GetMapping("/event-info/{id}")
    public ModelAndView eventInfoPage(@PathVariable("id") Long id) {
        Event event = eventService.getEvent(id);

        if(event == null) throw new EventNotFoundException();

        return new ModelAndView(
                "event-info",
                "event",
                new EventDTO(event)
        );
    }

    @GetMapping("/event-info-min/{id}")
    public ModelAndView eventInfoPageMinimal(@PathVariable("id") Long id) {
        Event event = eventService.getEvent(id);

        if(event == null) throw new EventNotFoundException();

        return new ModelAndView(
                "event-info-min",
                "event",
                new EventDTO(event)
        );
    }

    @GetMapping("/enroll/{id}")
    public String eventEnroll(@PathVariable("id") Long id) {
        if(!loggedUser.isLogged()) throw new AccessDeniedException();

        eventService.enroll(id);

        return "redirect:/browse";
    }

    @GetMapping("/remove/{id}")
    public String eventRemove(@PathVariable("id") Long id) {
        if(!loggedUser.isLogged()) throw new AccessDeniedException();
        if(!loggedUser.isCreator()) throw new AccessDeniedException();

        eventService.remove(id);

        return "redirect:/manage";
    }
}
