package com.devoops.oopslog.report.command.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportRequestDTO {
    private Long categoryId;   // 신고 카테고리
    private Long userId;       // 신고자
    private Long oohId;        // (선택)
    private Long oopsId;       // (선택)
    private Long commentId;    // (선택)
}
