package com.devoops.oopslog.recordhistory.query.controller;

import com.devoops.oopslog.recordhistory.query.dto.RequestRecordHistoryDTO;
import com.devoops.oopslog.recordhistory.query.service.RecordHistoryQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RecordHistoryQueryController {
    private final RecordHistoryQueryService recordHistoryQueryService;

    @Autowired
    public RecordHistoryQueryController(RecordHistoryQueryService recordHistoryQueryService) {
        this.recordHistoryQueryService = recordHistoryQueryService;
    }

    @GetMapping("/oops-daily-count")
    public ResponseEntity<List<RequestRecordHistoryDTO>> oopsDailyCount(){
        long userId = 20;   // 임시값
        List<RequestRecordHistoryDTO> recordHistoryList =
                recordHistoryQueryService.getOopsCalendarStats(userId);

        return ResponseEntity.ok().body(recordHistoryList);
    }
}
