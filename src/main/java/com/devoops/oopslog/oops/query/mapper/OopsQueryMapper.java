package com.devoops.oopslog.oops.query.mapper;

import com.devoops.oopslog.oops.query.dto.OopsQueryDTO;
import com.devoops.oopslog.oops.query.dto.OopsQuerySelectDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OopsQueryMapper {

    List<OopsQueryDTO> selectOopsList(
            @Param("title") String title,
            @Param("content") String content,
            @Param("name") String name,
            @Param("limit") int limit,
            @Param("offset") int offset);

    int selectOopsCount(
        @Param("title") String title,
        @Param("name") String name,
        @Param("content") String content
    );

    //  oopsId로 태그 id 목록 조회
    List<Long> selectTagIdsByOopsId(@Param("oopsId") Long oopsId);

    OopsQuerySelectDTO selectOopsRecordByOopsId(Long oopsId);
}
