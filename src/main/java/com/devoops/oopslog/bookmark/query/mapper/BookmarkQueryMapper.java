package com.devoops.oopslog.bookmark.query.mapper;

import com.devoops.oopslog.bookmark.query.dto.BookmarkItemDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BookmarkQueryMapper {

    /**
     * 특정 유저의 북마크 목록을 Ooh, Oops 구분하여 조회
     * @param userId 현재 유저 ID
     */
    List<BookmarkItemDto> findBookmarksByUserId(@Param("userId") Long userId);
}