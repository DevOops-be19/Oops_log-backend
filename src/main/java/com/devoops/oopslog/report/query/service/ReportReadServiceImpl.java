package com.devoops.oopslog.report.query.service;

import com.devoops.oopslog.report.query.dto.AllReportCategoryDTO;
import com.devoops.oopslog.report.query.dto.AllReportDTO;
import com.devoops.oopslog.report.query.dto.ReportDetailDTO;
import com.devoops.oopslog.report.query.mapper.ReportReadMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportReadServiceImpl implements ReportReadService {

    private ReportReadMapper reportReadMapper;

    @Autowired
    public ReportReadServiceImpl(ReportReadMapper reportReadMapper) {
        this.reportReadMapper = reportReadMapper;
    }

    @Override
    public List<AllReportDTO> getAllReport(int page, int size) {
        int offset = (page - 1) * size;
        return reportReadMapper.selectAllReport(size, offset);
    }

    @Override
    public ReportDetailDTO getReportDetailById(Long reportId) {
        return reportReadMapper.selectReportDetail(reportId);
    }

    @Override
    public List<AllReportCategoryDTO> selectAllReportCategory() {
        return reportReadMapper.selectReportCategory();
    }

}
