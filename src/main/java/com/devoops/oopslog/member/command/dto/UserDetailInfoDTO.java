package com.devoops.oopslog.member.command.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserDetailInfoDTO {
    private Long id;
    private String memberId;
    private String email;
    private String name;
    private String birth;
    private Character gender;
    private String signUpDate;
}
