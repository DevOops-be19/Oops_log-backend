package com.devoops.oopslog.report.query.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AllReportDTO {
    private Long id;
    private Date report_date;
    private String state;
    private Long category_id;
    private Long user_id;
    private Long ooh_id;
    private Long oops_id;
    private Long comment_id;
}
