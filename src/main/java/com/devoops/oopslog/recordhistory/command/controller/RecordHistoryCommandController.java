package com.devoops.oopslog.recordhistory.command.controller;

import com.devoops.oopslog.member.command.dto.UserImpl;
import com.devoops.oopslog.recordhistory.command.service.RecordHistoryCommandService;
import com.devoops.oopslog.recordhistory.query.service.RecordHistoryQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class RecordHistoryCommandController {
    private final RecordHistoryCommandService recordHistoryCommandService;

    @Autowired
    public RecordHistoryCommandController(RecordHistoryCommandService recordHistoryCommandService) {
        this.recordHistoryCommandService = recordHistoryCommandService;
    }

    @GetMapping("/count-ooh")
    public String dailyCountOohRecord() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication.getPrincipal() instanceof UserImpl)){
            throw new RuntimeException("잘못된 id");
        }
        UserImpl userImpl = (UserImpl)authentication.getPrincipal();


        long userId = userImpl.getId();
//        long userId = 20; // 임시값
        String result = recordHistoryCommandService.saveOohRecordHistory(userId);

        return result;
    }

    @GetMapping("/count-oops")
    public String dailyCountOopsRecord() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication.getPrincipal() instanceof UserImpl)){
            throw new RuntimeException("잘못된 id");
        }
        UserImpl userImpl = (UserImpl)authentication.getPrincipal();


        long userId = userImpl.getId();
//        long userId = 20; // 임시값
        String result = recordHistoryCommandService.saveOopsRecordHistory(userId);

        return result;
    }

}
