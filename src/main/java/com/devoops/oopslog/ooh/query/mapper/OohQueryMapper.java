package com.devoops.oopslog.ooh.query.mapper;

import com.devoops.oopslog.ooh.query.dto.OohMemIdDTO;
import com.devoops.oopslog.ooh.query.dto.OohQueryDTO;
import com.devoops.oopslog.ooh.query.dto.OohQuerySelectDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OohQueryMapper {

    List<OohQueryDTO> selectOohList(
            @Param("title") String title,
            @Param("content") String content,
            @Param("name") String name,
            @Param("limit") int limit,
            @Param("offset") int offset);

    int selectOohCount(
        @Param("title") String title,
        @Param("name") String name,
        @Param("content") String content
    );

    // oohId로 태그 id 목록 조회
    List<Long> selectTagIdsByOohId(@Param("oohId") Long oohId);

    OohQuerySelectDTO selectOohRecordByOohId(Long oohId);

    List<OohMemIdDTO> selectOohRecordByMemId(@Param("id") Long id);
}
