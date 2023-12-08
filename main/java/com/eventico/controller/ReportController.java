package com.eventico.controller;

import com.eventico.exceptions.AccessDeniedException;
import com.eventico.exceptions.ReportNotFoundException;
import com.eventico.model.dto.EventReportBinding;
import com.eventico.model.entity.Event;
import com.eventico.model.entity.Report;
import com.eventico.service.EventService;
import com.eventico.service.LoggedUser;
import com.eventico.service.ReportService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ReportController {
    private final LoggedUser loggedUser;
    private final EventService eventService;

    private final ReportService reportService;

    public ReportController(LoggedUser loggedUser, EventService eventRepository, ReportService reportService) {
        this.loggedUser = loggedUser;
        this.eventService = eventRepository;
        this.reportService = reportService;
    }

    @GetMapping("/report/{id}")
    public ModelAndView eventReport(@PathVariable("id") Long id) {
        if(!loggedUser.isLogged()) throw new AccessDeniedException();

        Event event = eventService.getEvent(id);

        return new ModelAndView("report", "event", event);
    }

    @PostMapping("/report/{id}")
    public String report(@PathVariable("id") Long id, @ModelAttribute("eventReportBindingModel") @Valid EventReportBinding eventReportBinding, BindingResult bindingResult) {
        reportService.reportEvent(id, eventReportBinding);

        return "redirect:/browse";
    }

    @GetMapping("/event-report-min/{id}")
    public ModelAndView eventReportPageMinimal(@PathVariable("id") Long id) {
        Report report = reportService.getReport(id);

        if(report == null) throw new ReportNotFoundException();

        return new ModelAndView(
                "event-report-min",
                "report",
                report
        );
    }

    @GetMapping("/report-approve/{id}")
    public String eventRemove(@PathVariable("id") Long id) {
        if(!loggedUser.isLogged()) throw new AccessDeniedException();
        if(!loggedUser.isAdmin()) throw new AccessDeniedException();

        reportService.approveReport(id);

        return "redirect:/manage";
    }
}
