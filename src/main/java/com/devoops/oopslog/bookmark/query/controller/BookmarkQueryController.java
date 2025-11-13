package com.devoops.oopslog.bookmark.query.controller;

import com.devoops.oopslog.bookmark.query.dto.BookmarkItemDto;
import com.devoops.oopslog.bookmark.query.service.BookmarkQueryService;
import com.devoops.oopslog.member.command.dto.UserImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bookmarks")
@RequiredArgsConstructor
public class BookmarkQueryController {

    private final BookmarkQueryService bookmarkQueryService;

    @GetMapping("/my")
    public ResponseEntity<List<BookmarkItemDto>> getMyBookmarks(
            @AuthenticationPrincipal UserImpl user
    ) {
        Long userId = user.getId();
        return ResponseEntity.ok(bookmarkQueryService.getBookmarks(userId));
    }
}