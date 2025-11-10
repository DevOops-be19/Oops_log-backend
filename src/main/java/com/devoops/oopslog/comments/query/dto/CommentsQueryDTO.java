package com.devoops.oopslog.comments.query.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CommentsQueryDTO {
    private Long id;
    private String content;
    private LocalDateTime create_date;
    private String is_deleted;
    private Long oops_id;
    private Long ooh_id;
    private Long user_id;
    private Long notice_id;
}
