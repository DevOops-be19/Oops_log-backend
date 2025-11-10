package com.devoops.oopslog.achivement.query.service;

import com.devoops.oopslog.achivement.query.dto.AchivementSummaryDTO;
import com.devoops.oopslog.achivement.query.dto.OohRecordCountDTO;
import com.devoops.oopslog.achivement.query.dto.OopsRecordCountDTO;

import java.util.List;

public interface AchivementReadService {
    List<OopsRecordCountDTO> getDailyUserOopsRecord(Long userId, int year, int month);
    List<OohRecordCountDTO> getDailyUserOohRecord(Long userId, int year, int month);
    AchivementSummaryDTO getUserAchivementSummary(Long userId);
}
