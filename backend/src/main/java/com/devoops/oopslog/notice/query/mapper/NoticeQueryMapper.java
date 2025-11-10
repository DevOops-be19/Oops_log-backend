package com.devoops.oopslog.notice.query.mapper;

import com.devoops.oopslog.notice.query.dto.NoticeQueryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NoticeQueryMapper {

    List<NoticeQueryDTO> selectNoticeList(
            @Param("title") String title,
            @Param("content") String content,
            @Param("limit") int limit,
            @Param("offset") int offset
    );

    int selectNoticeCount(
            @Param("title") String title,
            @Param("content") String content
    );

}
