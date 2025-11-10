package com.devoops.oopslog.bookmark.query.controller;

import com.devoops.oopslog.bookmark.query.dto.BookmarkItemDto;
import com.devoops.oopslog.bookmark.query.service.BookmarkQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/bookmarks")
@RequiredArgsConstructor
public class BookmarkQueryController {

    private final BookmarkQueryService bookmarkQueryService;

    /**
     * 특정 유저의 북마크 목록 조회 (Read)
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookmarkItemDto>> getMyBookmarks(@PathVariable Long userId) {
        return ResponseEntity.ok(bookmarkQueryService.getBookmarks(userId));
    }
}