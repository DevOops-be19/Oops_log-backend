package com.devoops.oopslog.achivement.query.controller;

import com.devoops.oopslog.achivement.query.dto.AchivementInfoDTO;
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

    @GetMapping("/{userId}")
    public ResponseEntity<AchivementInfoDTO> getAchivementInfo(@PathVariable Long userId) {
        AchivementInfoDTO result = achivementReadService.getAchivementInfo(userId);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/{userId}/daily")
    public ResponseEntity<AchivementSummaryDTO> getDailyAchivement(
            @PathVariable Long userId,
            @RequestParam int year,
            @RequestParam int month) {
        AchivementSummaryDTO result = achivementReadService.getUserAchivementSummary(userId, year, month);
        return ResponseEntity.ok().body(result);
    }

}
