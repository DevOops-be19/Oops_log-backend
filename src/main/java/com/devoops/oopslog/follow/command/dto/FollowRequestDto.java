package com.devoops.oopslog.follow.command.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FollowRequestDto {
    // 팔로우/언팔로우 주체
    private Long followerId;

    // 팔로우/언팔로우 대상
    private Long followeeId;
}