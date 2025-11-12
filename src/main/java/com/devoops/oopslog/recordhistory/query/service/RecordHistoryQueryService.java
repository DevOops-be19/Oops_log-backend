package com.devoops.oopslog.recordhistory.query.service;

import com.devoops.oopslog.recordhistory.query.dto.RequestRecordHistoryDTO;

import java.util.List;

public interface RecordHistoryQueryService {
    List<RequestRecordHistoryDTO> getOopsCalendarStats(long userId);

    List<RequestRecordHistoryDTO> getOohCalendarStats(long userId);
}
