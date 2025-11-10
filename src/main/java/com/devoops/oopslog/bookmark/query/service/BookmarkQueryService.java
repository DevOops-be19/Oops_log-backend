package com.devoops.oopslog.bookmark.query.service;

import com.devoops.oopslog.bookmark.query.dto.BookmarkItemDto;
import com.devoops.oopslog.bookmark.query.mapper.BookmarkQueryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookmarkQueryService {

    private final BookmarkQueryMapper bookmarkQueryMapper; // MyBatis

    /**
     * 북마크 목록 조회 R (MyBatis)
     */
    @Transactional(readOnly = true)
    public List<BookmarkItemDto> getBookmarks(Long userId) {
        return bookmarkQueryMapper.findBookmarksByUserId(userId);
    }
}