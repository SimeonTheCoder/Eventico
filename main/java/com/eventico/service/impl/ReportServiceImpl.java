package com.eventico.service.impl;

import com.eventico.model.dto.EventReportBinding;
import com.eventico.model.entity.Event;
import com.eventico.model.entity.Report;
import com.eventico.repo.EventRepository;
import com.eventico.repo.ReportRepository;
import com.eventico.repo.UserRepository;
import com.eventico.service.LoggedUser;
import com.eventico.service.ReportService;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
public class ReportServiceImpl implements ReportService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final ReportRepository reportRepository;
    private final LoggedUser loggedUser;

    public ReportServiceImpl(EventRepository eventRepository, UserRepository userRepository, ReportRepository reportRepository, LoggedUser loggedUser) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.reportRepository = reportRepository;
        this.loggedUser = loggedUser;
    }

    @Override
    public boolean reportEvent(Long id, EventReportBinding binding) {
        if(!loggedUser.isLogged()) return false;

        Event event = eventRepository.findById(id).orElse(null);

        if(event == null) return false;

        Report report = new Report();

        report.setReason(binding.getReason());
        report.setDescription(binding.getDescription());
        report.setReportedEvent(event);
        report.setReportTime(LocalDateTime.now());
        report.setReportedBy(userRepository.findByUsername(loggedUser.getUsername()));

        reportRepository.save(report);

        return true;
    }
}
