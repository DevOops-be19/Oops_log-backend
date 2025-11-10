package com.devoops.oopslog.report.query.dto;

import lombok.Data;

@Data
public class ReportDetailDTO {
    private Long id;
    private String state;
    private Long user_id;
    private Long target_id;
    private String target_content;
}
