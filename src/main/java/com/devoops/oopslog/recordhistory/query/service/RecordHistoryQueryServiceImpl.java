package com.devoops.oopslog.recordhistory.query.service;

import com.devoops.oopslog.recordhistory.query.dto.RequestRecordHistoryDTO;
import com.devoops.oopslog.recordhistory.query.mapper.RecordHistoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordHistoryQueryServiceImpl implements RecordHistoryQueryService {
    private final RecordHistoryMapper recordHistoryMapper;

    @Autowired
    public RecordHistoryQueryServiceImpl(RecordHistoryMapper recordHistoryMapper) {
        this.recordHistoryMapper = recordHistoryMapper;
    }

    @Override
    public List<RequestRecordHistoryDTO> getOopsCalendarStats(long userId) {
        List<RequestRecordHistoryDTO> recordHistoryList =
                recordHistoryMapper.dailyCountOopsByUser(userId);

        return recordHistoryList;
    }

    @Override
    public List<RequestRecordHistoryDTO> getOohCalendarStats(long userId) {
        List<RequestRecordHistoryDTO> recordHistoryList =
                recordHistoryMapper.dailyCountOohByUser(userId);

        return recordHistoryList;
    }
}
