package com.devoops.oopslog.follow.query.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FollowerResponseDto {
    private Long id; // member PK
    private String memberId; // member 로그인 ID
    private String name; // member 이름
}