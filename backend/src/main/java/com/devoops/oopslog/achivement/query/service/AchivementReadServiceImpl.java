package com.devoops.oopslog.achivement.query.service;

import com.devoops.oopslog.achivement.query.dto.AchivementSummaryDTO;
import com.devoops.oopslog.achivement.query.dto.OohRecordCountDTO;
import com.devoops.oopslog.achivement.query.dto.OopsRecordCountDTO;
import com.devoops.oopslog.achivement.query.dto.TagCountDTO;
import com.devoops.oopslog.achivement.query.mapper.AchivementReadMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AchivementReadServiceImpl implements AchivementReadService {

    private AchivementReadMapper achivementReadMapper;

    @Autowired
    public AchivementReadServiceImpl(AchivementReadMapper achivementReadMapper) {
        this.achivementReadMapper = achivementReadMapper;
    }


    @Override
    public List<OopsRecordCountDTO> getDailyUserOopsRecord(Long userId, int year, int month) {
        return achivementReadMapper.selectDailyUserOopsRecord(userId, year, month);
    }

    @Override
    public List<OohRecordCountDTO> getDailyUserOohRecord(Long userId, int year, int month) {
        return achivementReadMapper.selectDailyUserOohRecord(userId, year, month);
    }

    @Override
    public AchivementSummaryDTO getUserAchivementSummary(Long userId) {
        int oopsCount = achivementReadMapper.countOopsRecord(userId);
        int oohCount = achivementReadMapper.countOohRecord(userId);

        List<TagCountDTO> topOopsTags = achivementReadMapper.selectTopOopsTag(userId);
        List<TagCountDTO> topOohTags = achivementReadMapper.selectTopOohTag(userId);

        AchivementSummaryDTO summary = new AchivementSummaryDTO();
        summary.setOopsCount(oopsCount);
        summary.setOohCount(oohCount);
        summary.setTopOopsTags(topOopsTags);
        summary.setTopOohTags(topOohTags);

        return summary;
    }
}
