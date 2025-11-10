package com.devoops.oopslog.report.command.service;

import com.devoops.oopslog.report.command.dto.ReportRequestDTO;
import com.devoops.oopslog.report.command.entity.ReportEntity;

public interface ReportService {
    ReportEntity createReport(ReportRequestDTO dto);

    void updateReportState(Long reportId, String state);
}
