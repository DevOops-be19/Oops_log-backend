package com.devoops.oopslog.recordhistory.command.controller;

import com.devoops.oopslog.recordhistory.command.service.RecordHistoryCommandService;
import com.devoops.oopslog.recordhistory.query.service.RecordHistoryQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecordHistoryCommandController {
    private final RecordHistoryCommandService recordHistoryCommandService;

    @Autowired
    public RecordHistoryCommandController(RecordHistoryCommandService recordHistoryCommandService) {
        this.recordHistoryCommandService = recordHistoryCommandService;
    }

    @GetMapping("/count-ooh")
    public void dailyCountOohRecord() {
        long userId = 20; // 임시값
//        recordHistoryCommandService.saveRecordHistory(userId, );
    }

}
