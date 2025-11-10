package com.devoops.oopslog.follow.query.service;

import com.devoops.oopslog.follow.query.dto.FollowerResponseDto;
import com.devoops.oopslog.follow.query.mapper.FollowQueryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FollowQueryService {

    private final FollowQueryMapper followQueryMapper; // MyBatis

    /**
     * 팔로잉 목록 조회 R (MyBatis)
     */
    @Transactional(readOnly = true)
    public List<FollowerResponseDto> getFollowingList(Long userId) {
        return followQueryMapper.findFolloweesByUserId(userId);
    }

    /**
     * 팔로워 목록 조회 R (MyBatis)
     */
    @Transactional(readOnly = true)
    public List<FollowerResponseDto> getFollowerList(Long userId) {
        return followQueryMapper.findFollowersByUserId(userId);
    }
}