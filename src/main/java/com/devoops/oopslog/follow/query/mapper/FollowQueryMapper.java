package com.devoops.oopslog.follow.query.mapper;

import com.devoops.oopslog.follow.query.dto.FollowerResponseDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FollowQueryMapper {

    /**
     * 내가 팔로우하는 사람들 (팔로잉 목록)
     * @param userId 현재 유저 ID
     */
    List<FollowerResponseDto> findFolloweesByUserId(@Param("userId") Long userId);

    /**
     * 나를 팔로우하는 사람들 (팔로워 목록)
     * @param userId 현재 유저 ID
     */
    List<FollowerResponseDto> findFollowersByUserId(@Param("userId") Long userId);
}