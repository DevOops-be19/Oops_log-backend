package com.devoops.oopslog.oops.query.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OopsDetailCommentDTO {
    private Long id;
    private String author;       // 익명 표기
    private String content;
    private LocalDateTime createdAt;
}