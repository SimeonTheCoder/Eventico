package com.eventico.controller;

import com.eventico.model.dto.BrowseSelectionFilterBinding;
import com.eventico.service.EventService;
import com.eventico.service.impl.LoggedUser;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
    private final LoggedUser loggedUser;
    private final EventService eventService;

    public HomeController(LoggedUser loggedUser, EventService eventService) {
        this.loggedUser = loggedUser;
        this.eventService = eventService;
    }

    @GetMapping("/home")
    public ModelAndView homePage() {
        return new ModelAndView("home");
    }

    @GetMapping("/browse")
    public ModelAndView browsePage() {
        return new ModelAndView("browse", "events", eventService.getHomeData());
    }

    @PostMapping("/browse")
    public String filterSearch(@ModelAttribute("browseSelectionFilterBindingModel") @Valid BrowseSelectionFilterBinding browseSelectionFilterBinding, BindingResult bindingResult) {
        eventService.filterSearch(browseSelectionFilterBinding);
        return "redirect:/browse";
    }

    @GetMapping("/")
    public ModelAndView indexPage(){
        return new ModelAndView("index");
    }
}
