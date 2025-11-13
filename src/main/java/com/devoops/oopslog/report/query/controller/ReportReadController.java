package com.devoops.oopslog.report.query.controller;


import com.devoops.oopslog.report.query.dto.AllReportCategoryDTO;
import com.devoops.oopslog.report.query.dto.AllReportDTO;
import com.devoops.oopslog.report.query.dto.ReportDetailDTO;
import com.devoops.oopslog.report.query.service.ReportReadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/report")
public class ReportReadController {

    private final ReportReadService reportReadService;

    @Autowired
    public ReportReadController(ReportReadService reportReadService) {
        this.reportReadService = reportReadService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<AllReportDTO>> getAllReport(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size
    ){
        List<AllReportDTO> result = reportReadService.getAllReport(page, size);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/{reportId}")
    public ResponseEntity<ReportDetailDTO> getReportDetailById(@PathVariable("reportId") Long reportId) {
        ReportDetailDTO result = reportReadService.getReportDetailById(reportId);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/category")
    public ResponseEntity<List<AllReportCategoryDTO>> getAllReportCategory() {
        List<AllReportCategoryDTO> result = reportReadService.selectAllReportCategory();
        return ResponseEntity.ok().body(result);
    }



}
