package com.eventico.controller;

import com.eventico.exceptions.AccessDeniedException;
import com.eventico.model.dto.BrowseSelectionFilterBinding;
import com.eventico.service.EventService;
import com.eventico.service.LoggedUser;
import com.eventico.service.ReportService;
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
    private final ReportService reportService;

    public HomeController(LoggedUser loggedUser, EventService eventService, ReportService reportService) {
        this.loggedUser = loggedUser;
        this.eventService = eventService;
        this.reportService = reportService;
    }

    @GetMapping("/home")
    public ModelAndView homePage() {
        if(!loggedUser.isLogged()) throw new AccessDeniedException();
        return new ModelAndView("home", "feed", eventService.getEventsForUser(loggedUser.getUsername()));
    }

    @GetMapping("/browse")
    public ModelAndView browsePage() {
        return new ModelAndView("browse", "events", eventService.getHomeData());
    }

    @GetMapping("/manage")
    public ModelAndView managePage() {
        if(!loggedUser.isLogged()) throw new AccessDeniedException();
        if(!loggedUser.isCreator()) throw new AccessDeniedException();
        return new ModelAndView("manage", "events", eventService.getAll());
    }

    @GetMapping("/manage-reports")
    public ModelAndView manageReports() {
        if(!loggedUser.isLogged()) throw new AccessDeniedException();
        if(!loggedUser.isCreator()) throw new AccessDeniedException();
        return new ModelAndView("manage-reports", "reports", reportService.getAll());
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
