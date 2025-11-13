package com.devoops.oopslog.follow.query.controller;

import com.devoops.oopslog.follow.query.dto.FollowFeedItemDto;
import com.devoops.oopslog.follow.query.dto.FollowerResponseDto;
import com.devoops.oopslog.follow.query.service.FollowQueryService;
import com.devoops.oopslog.member.command.dto.UserImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/follow")
@RequiredArgsConstructor
public class FollowQueryController {

    private final FollowQueryService followQueryService;

    @GetMapping("/following/my")    // 특정 유저의 팔로우 목록
    public ResponseEntity<List<FollowerResponseDto>> getFollowingList(
            @AuthenticationPrincipal UserImpl user
    ) {
        Long userId = user.getId();
        return ResponseEntity.ok(followQueryService.getFollowingList(userId));
    }

    @GetMapping("/followers/my")    // 특정 유저의 팔로잉 목록
    public ResponseEntity<List<FollowerResponseDto>> getFollowerList(
            @AuthenticationPrincipal UserImpl user
    ) {
        Long userId = user.getId();
        return ResponseEntity.ok(followQueryService.getFollowerList(userId));
    }

    @GetMapping("/feed/my")         // 팔로잉 피드 조회 API 엔드포인트
    public ResponseEntity<List<FollowFeedItemDto>> getMyFollowingFeed(
            @AuthenticationPrincipal UserImpl user
    ) {
        Long userId = user.getId();
        return ResponseEntity.ok(followQueryService.getFollowingFeed(userId));
    }
}