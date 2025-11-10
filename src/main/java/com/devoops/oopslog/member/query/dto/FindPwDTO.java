package com.devoops.oopslog.member.query.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class FindPwDTO {
    private String memberId;
    private String email;
    private String name;
    private String birth;
}
