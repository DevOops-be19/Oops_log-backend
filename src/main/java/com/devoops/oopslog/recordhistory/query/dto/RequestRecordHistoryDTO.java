package com.devoops.oopslog.recordhistory.query.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RequestRecordHistoryDTO {
    private Long id;
    private LocalDateTime count_date;
    private int record_count;
}
