package com.devoops.oopslog.member.query.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class FindMemberDTO {
    private Long id;
    private String memberId;
    private String email;
    private Character user_state;

}
