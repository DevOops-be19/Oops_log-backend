package com.devoops.oopslog.notice.query.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class NoticeQueryScrollResponseDTO {
    private List<NoticeQueryDTO> noticeList;
    private boolean hasNextPage;
    private int totalCount;  // total 갯수
}
