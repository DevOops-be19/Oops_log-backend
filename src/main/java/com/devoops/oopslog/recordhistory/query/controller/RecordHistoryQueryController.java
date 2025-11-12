package com.devoops.oopslog.recordhistory.query.controller;

import com.devoops.oopslog.member.command.dto.UserImpl;
import com.devoops.oopslog.recordhistory.query.dto.RequestRecordHistoryDTO;
import com.devoops.oopslog.recordhistory.query.service.RecordHistoryQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication.getPrincipal() instanceof UserImpl)){
            throw new RuntimeException("잘못된 id");
        }
        UserImpl userImpl = (UserImpl)authentication.getPrincipal();


        long userId = userImpl.getId();
//        long userId = 20;   // 임시값
        List<RequestRecordHistoryDTO> recordHistoryList =
                recordHistoryQueryService.getOopsCalendarStats(userId);

        return ResponseEntity.ok().body(recordHistoryList);
    }

    @GetMapping("/ooh-daily-count")
    public ResponseEntity<List<RequestRecordHistoryDTO>> oohDailyCount(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication.getPrincipal() instanceof UserImpl)){
            throw new RuntimeException("잘못된 id");
        }
        UserImpl userImpl = (UserImpl)authentication.getPrincipal();


        long userId = userImpl.getId();
//        long userId = 20;   // 임시값
        List<RequestRecordHistoryDTO> recordHistoryList =
                recordHistoryQueryService.getOohCalendarStats(userId);

        return ResponseEntity.ok().body(recordHistoryList);
    }
}
