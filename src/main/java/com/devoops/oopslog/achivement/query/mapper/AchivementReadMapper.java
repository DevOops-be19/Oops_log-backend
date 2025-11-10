package com.devoops.oopslog.achivement.query.mapper;

import com.devoops.oopslog.achivement.query.dto.OohRecordCountDTO;
import com.devoops.oopslog.achivement.query.dto.OopsRecordCountDTO;
import com.devoops.oopslog.achivement.query.dto.TagCountDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AchivementReadMapper {
    List<OopsRecordCountDTO> selectDailyUserOopsRecord(
            @Param("userId") Long userId,
            @Param("year") int year,
            @Param("month") int month
    );

    List<OohRecordCountDTO> selectDailyUserOohRecord(
            @Param("userId") Long userId,
            @Param("year") int year,
            @Param("month") int month
    );

    // Oops / Ooh 태그 Top5
    List<TagCountDTO> selectTopOopsTag(@Param("userId") Long userId);
    List<TagCountDTO> selectTopOohTag(@Param("userId") Long userId);

    // Oops / Ooh 게시글 개수
    int countOopsRecord(@Param("userId") Long userId);
    int countOohRecord(@Param("userId") Long userId);
}
