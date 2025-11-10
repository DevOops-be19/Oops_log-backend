package com.devoops.oopslog.achivement.query.controller;

import com.devoops.oopslog.achivement.query.dto.AchivementSummaryDTO;
import com.devoops.oopslog.achivement.query.dto.OohRecordCountDTO;
import com.devoops.oopslog.achivement.query.dto.OopsRecordCountDTO;
import com.devoops.oopslog.achivement.query.service.AchivementReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/achivement")
public class AchivementReadController {

    private final AchivementReadService achivementReadService;

    @Autowired
    public AchivementReadController(AchivementReadService achivementReadService) {
        this.achivementReadService = achivementReadService;
    }

    @GetMapping("/{userId}/daily")
    public ResponseEntity<Map<String, Object>> getDailyAchivement(
            @PathVariable Long userId,
            @RequestParam int year,
            @RequestParam int month) {
        List<OopsRecordCountDTO> oopsList = achivementReadService.getDailyUserOopsRecord(userId, year, month);
        List<OohRecordCountDTO> oohList = achivementReadService.getDailyUserOohRecord(userId, year, month);

        Map<String, Object> result = new HashMap<>();
        result.put("oops", oopsList);
        result.put("ooh", oohList);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/summary/{userId}")
    public ResponseEntity<AchivementSummaryDTO> getSummaryAchivement(
            @PathVariable Long userId
    ){
        AchivementSummaryDTO result = achivementReadService.getUserAchivementSummary(userId);
        return ResponseEntity.ok().body(result);
    }
}
