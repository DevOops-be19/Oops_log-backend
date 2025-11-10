package com.devoops.oopslog.follow.command.controller;

import com.devoops.oopslog.follow.command.dto.FollowRequestDto;
import com.devoops.oopslog.follow.command.service.FollowCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/follow")
@RequiredArgsConstructor
public class FollowCommandController {

    private final FollowCommandService followCommandService;

    /**
     * 다른 유저 팔로우 (Create)
     */
    @PostMapping
    public ResponseEntity<String> follow(
            @RequestBody FollowRequestDto request) { // 1. DTO로 모든 정보를 받음
        try {
            // 2. DTO에서 followerId와 followeeId를 모두 가져와 서비스로 전달
            followCommandService.follow(request.getFollowerId(), request.getFolloweeId());
            return ResponseEntity.ok("팔로우를 성공했습니다.");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    /**
     * 팔로우 취소 (Delete)
     */
    @DeleteMapping
    public ResponseEntity<String> unfollow(
            @RequestBody FollowRequestDto request) { // 1. DTO로 모든 정보를 받음
        try {
            // 2. DTO에서 followerId와 followeeId를 모두 가져와 서비스로 전달
            followCommandService.unfollow(request.getFollowerId(), request.getFolloweeId());
            return ResponseEntity.ok("언팔로우를 성공했습니다.");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}