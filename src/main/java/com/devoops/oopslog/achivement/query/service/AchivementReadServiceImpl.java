package com.devoops.oopslog.achivement.query.service;

import com.devoops.oopslog.achivement.query.dto.*;
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
    public AchivementInfoDTO getAchivementInfo(Long userId) {
        int oopsCount = achivementReadMapper.countOopsRecord(userId);
        int oohCount = achivementReadMapper.countOohRecord(userId);
        List<FindYearMonthDTO> findYearOops = achivementReadMapper.findYearMonthOopsById(userId);
        List<FindYearMonthDTO> findYearOoh = achivementReadMapper.findYearMonthOohById(userId);

        AchivementInfoDTO achivementInfoDTO = new AchivementInfoDTO();
        achivementInfoDTO.setOopsCount(oopsCount);
        achivementInfoDTO.setOohCount(oohCount);
        achivementInfoDTO.setFindYearOops(findYearOops);
        achivementInfoDTO.setFindYearOoh(findYearOoh);

        return achivementInfoDTO;
    }

    @Override
    public AchivementSummaryDTO getUserAchivementSummary(Long userId, int year, int month) {
        List<OopsRecordCountDTO> topOopsRecords = achivementReadMapper.selectDailyUserOopsRecord(userId, year, month);
        List<OohRecordCountDTO> topOohRecords =  achivementReadMapper.selectDailyUserOohRecord(userId, year, month);
        List<TagCountDTO> topOopsTags = achivementReadMapper.selectTopOopsTagByMonth(userId, year, month);
        List<TagCountDTO> topOohTags = achivementReadMapper.selectTopOohTagByMonth(userId, year, month);

        AchivementSummaryDTO summary = new AchivementSummaryDTO();
        summary.setOopsRecords(topOopsRecords);
        summary.setOohRecords(topOohRecords);
        summary.setTopOopsTags(topOopsTags);
        summary.setTopOohTags(topOohTags);

        return summary;
    }
}
