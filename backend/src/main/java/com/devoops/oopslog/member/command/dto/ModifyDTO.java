package com.devoops.oopslog.member.command.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ModifyDTO {
    private Long id;
    private String memberId;
    private String memberPw;
    private String email;
}
