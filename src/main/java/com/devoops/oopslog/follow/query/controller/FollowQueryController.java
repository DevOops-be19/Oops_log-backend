package com.devoops.oopslog.follow.query.controller;

import com.devoops.oopslog.follow.query.dto.FollowerResponseDto;
import com.devoops.oopslog.follow.query.service.FollowQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/follow")
@RequiredArgsConstructor
public class FollowQueryController {

    private final FollowQueryService followQueryService;

    /**
     * 특정 유저의 팔로잉 목록 조회 (Read)
     */
    @GetMapping("/following/{userId}")
    public ResponseEntity<List<FollowerResponseDto>> getFollowingList(@PathVariable Long userId) {
        return ResponseEntity.ok(followQueryService.getFollowingList(userId));
    }

    /**
     * 특정 유저의 팔로워 목록 조회 (Read)
     */
    @GetMapping("/followers/{userId}")
    public ResponseEntity<List<FollowerResponseDto>> getFollowerList(@PathVariable Long userId) {
        return ResponseEntity.ok(followQueryService.getFollowerList(userId));
    }
}