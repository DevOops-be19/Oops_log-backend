package com.devoops.oopslog.ooh.query.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OohQuerySelectDTO {
    private Long id;
    private String is_private;
    private String title;
    private String content;
    private String ai_answer;
    private LocalDateTime create_date;
    private LocalDateTime modify_date;
    private String is_deleted;
    private Long user_id;
    private String user_name;
}
