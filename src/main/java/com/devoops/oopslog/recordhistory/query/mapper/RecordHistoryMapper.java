package com.devoops.oopslog.recordhistory.query.mapper;

import com.devoops.oopslog.recordhistory.query.dto.RequestRecordHistoryDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RecordHistoryMapper {
    List<RequestRecordHistoryDTO> dailyCountOopsByUser(long userId);
}
