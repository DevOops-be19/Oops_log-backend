package com.devoops.oopslog.notice.query.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class NoticeQueryDTO {
    //    id, title, content, create_date, modify_date, is_deleted, user_id(FK)

    private Long noticeId;

    private String noticeTitle;

    private String noticeContent;

    private LocalDateTime noticeCreateDate;

    private LocalDateTime noticeModifyDate;

    private String noticeIsDeleted;

    private String name;


    // 댓글 추가해야함
}
