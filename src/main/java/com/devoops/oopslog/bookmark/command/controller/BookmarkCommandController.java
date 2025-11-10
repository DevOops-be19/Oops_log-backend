package com.devoops.oopslog.bookmark.command.controller;

import com.devoops.oopslog.bookmark.command.dto.BookmarkRequestDto;
import com.devoops.oopslog.bookmark.command.service.BookmarkCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/bookmarks")
@RequiredArgsConstructor
public class BookmarkCommandController {

    private final BookmarkCommandService bookmarkCommandService;

    /**
     * Ooh/Oops 게시글 북마크 추가 (Create)
     */
    @PostMapping
    public ResponseEntity<String> addBookmark(@RequestBody BookmarkRequestDto request) {
        try {
            bookmarkCommandService.addBookmark(request);
            return ResponseEntity.ok("북마크 추가를 성공했습니다.");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (IllegalStateException | IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    /**
     * 북마크 삭제 (Delete)
     */
    @DeleteMapping
    public ResponseEntity<String> removeBookmark(@RequestBody BookmarkRequestDto request) {
        try {
            bookmarkCommandService.removeBookmark(request);
            return ResponseEntity.ok("북마크 삭제를 성공했습니다.");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}