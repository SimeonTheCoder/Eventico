package com.eventico.service;

import com.eventico.model.dto.EventReportBinding;

public interface ReportService {
    boolean reportEvent(Long id, EventReportBinding binding);
}
