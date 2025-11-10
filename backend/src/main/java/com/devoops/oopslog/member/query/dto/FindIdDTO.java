package com.devoops.oopslog.member.query.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class FindIdDTO {
    private String email;
    private String name;
    private String birth;
}
