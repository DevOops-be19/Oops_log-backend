package com.devoops.oopslog.bookmark.command.controller;

import com.devoops.oopslog.bookmark.command.dto.BookmarkRequestDto;
import com.devoops.oopslog.bookmark.command.service.BookmarkCommandService;
import com.devoops.oopslog.member.command.dto.UserImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/bookmarks")
@RequiredArgsConstructor
public class BookmarkCommandController {

    private final BookmarkCommandService bookmarkCommandService;

    @PostMapping
    public ResponseEntity<String> addBookmark(
            @AuthenticationPrincipal UserImpl user,
            @RequestBody BookmarkRequestDto request) {
        try {
            Long userId = user.getId();
            bookmarkCommandService.addBookmark(userId, request);
            return ResponseEntity.ok("북마크 추가를 성공했습니다.");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (IllegalStateException | IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<String> removeBookmark(
            @AuthenticationPrincipal UserImpl user,
            @RequestBody BookmarkRequestDto request) {
        try {
            Long userId = user.getId();
            bookmarkCommandService.removeBookmark(userId, request);
            return ResponseEntity.ok("북마크 삭제를 성공했습니다.");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}