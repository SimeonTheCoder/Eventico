package com.eventico.service;

import com.eventico.model.dto.EventReportBinding;
import com.eventico.model.entity.Report;

import java.util.List;

public interface ReportService {
    boolean reportEvent(Long id, EventReportBinding binding);

    Report getReport(Long id);

    boolean approveReport(Long id);

    List<Report> getAll();
}
