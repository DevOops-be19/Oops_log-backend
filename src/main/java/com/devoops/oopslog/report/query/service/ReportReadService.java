package com.devoops.oopslog.report.query.service;


import com.devoops.oopslog.report.query.dto.AllReportCategoryDTO;
import com.devoops.oopslog.report.query.dto.AllReportDTO;
import com.devoops.oopslog.report.query.dto.ReportDetailDTO;

import java.util.List;

public interface ReportReadService {
    List<AllReportDTO> getAllReport(int page, int size);
    ReportDetailDTO getReportDetailById(Long reportId);
    List<AllReportCategoryDTO> selectAllReportCategory();
}
