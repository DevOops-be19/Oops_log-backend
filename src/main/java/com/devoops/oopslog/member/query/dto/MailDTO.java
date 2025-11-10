package com.devoops.oopslog.member.query.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MailDTO {
    private String address;
    private String subject;
    private String content;
}
