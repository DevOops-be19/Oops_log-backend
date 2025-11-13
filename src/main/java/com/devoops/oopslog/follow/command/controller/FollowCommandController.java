package com.devoops.oopslog.follow.command.controller;

import com.devoops.oopslog.follow.command.dto.FollowRequestDto;
import com.devoops.oopslog.follow.command.service.FollowCommandService;
import com.devoops.oopslog.member.command.dto.UserImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/follow")
@RequiredArgsConstructor
public class FollowCommandController {

    private final FollowCommandService followCommandService;

    @PostMapping
    public ResponseEntity<String> follow(
            @AuthenticationPrincipal UserImpl user,
            @RequestBody FollowRequestDto request) {
        try {
            Long followerId = user.getId();
            followCommandService.follow(followerId, request.getFolloweeId());
            return ResponseEntity.ok("팔로우를 성공했습니다.");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<String> unfollow(
            @AuthenticationPrincipal UserImpl user,
            @RequestBody FollowRequestDto request) {
        try {
            Long followerId = user.getId();
            followCommandService.unfollow(followerId, request.getFolloweeId());
            return ResponseEntity.ok("언팔로우를 성공했습니다.");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}