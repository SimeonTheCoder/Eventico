package com.eventico.service.impl;

import com.eventico.model.dto.EventReportBinding;
import com.eventico.model.entity.Event;
import com.eventico.model.entity.Report;
import com.eventico.repo.CityRepository;
import com.eventico.repo.EventRepository;
import com.eventico.repo.ReportRepository;
import com.eventico.repo.UserRepository;
import com.eventico.service.LoggedUser;
import com.eventico.service.ReportService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Controller
public class ReportServiceImpl implements ReportService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final ReportRepository reportRepository;
    private final CityRepository cityRepository;
    private final LoggedUser loggedUser;

    public ReportServiceImpl(EventRepository eventRepository, UserRepository userRepository, ReportRepository reportRepository, CityRepository cityRepository, LoggedUser loggedUser) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.reportRepository = reportRepository;
        this.cityRepository = cityRepository;
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

    @Override
    public Report getReport(Long id) {
        return reportRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public boolean approveReport(Long id) {
        if(!loggedUser.isLogged()) return false;
        if(!loggedUser.isAdmin()) return false;

        Report report = reportRepository.findById(id).orElse(null);
        if(report == null) return false;

        Event event = eventRepository.findById(report.getReportedEvent().getId()).orElse(null);
        if(event == null) return false;

        reportRepository.findAll().forEach((r) -> {
             if(Objects.equals(r.getReportedEvent().getId(), event.getId())) {
                 reportRepository.deleteById(r.getId());
             }
        });

        event.getCity().getEvents().remove(event);
        cityRepository.save(event.getCity());

        eventRepository.deleteById(event.getId());

        return true;
    }

    @Override
    public List<Report> getAll() {
        return reportRepository.findAll();
    }
}
