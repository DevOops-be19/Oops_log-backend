package com.devoops.oopslog.notice.command.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class NoticeCommandCreateDTO {

    //    id, title, content, create_date, modify_date, is_deleted, user_id(FK)

    private Long noticeId;

    private String noticeTitle;

    private String noticeContent;

    private LocalDateTime noticeCreateDate;

    private LocalDateTime noticeModifyDate;

    private String noticeIsDeleted;

    private Long noticeUserId;

    private String name;

}
