package com.devoops.oopslog.achivement.query.service;

import com.devoops.oopslog.achivement.query.dto.AchivementInfoDTO;
import com.devoops.oopslog.achivement.query.dto.AchivementSummaryDTO;
import com.devoops.oopslog.achivement.query.dto.OohRecordCountDTO;
import com.devoops.oopslog.achivement.query.dto.OopsRecordCountDTO;

import java.util.List;

public interface AchivementReadService {
    AchivementInfoDTO getAchivementInfo(Long userId);
    AchivementSummaryDTO getUserAchivementSummary(Long userId, int year, int month);
}
