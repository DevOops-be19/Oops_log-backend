package com.devoops.oopslog.member.command.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SignUpDTO {
    private String memberId;
    private String memberPw;
    private String email;
    private String name;
    private String birth;
    private Character gender;
}
