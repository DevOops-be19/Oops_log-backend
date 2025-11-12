package com.devoops.oopslog.ooh.query.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OohDetailCommentDTO {
    private Long id;
    private String author;       // 익명 표기
    private String content;
    private LocalDateTime createdAt;
}