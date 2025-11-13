package com.devoops.oopslog.follow.query.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class FollowerResponseDto {
    private Long id; // member PK
    private String memberId; // member 로그인 ID
    private String name; // member 이름
}