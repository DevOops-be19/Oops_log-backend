package com.devoops.oopslog.recordhistory.command.service;

import com.devoops.oopslog.ooh.command.repository.OohCommandRepository;
import com.devoops.oopslog.recordhistory.command.entity.RecordHistory;
import com.devoops.oopslog.recordhistory.command.repository.RecordHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class RecordHistoryCommandServiceImpl implements RecordHistoryCommandService {
    private final OohCommandRepository oohCommandRepository;
    private final RecordHistoryRepository recordHistoryRepository;

    @Autowired
    public RecordHistoryCommandServiceImpl(OohCommandRepository oohCommandRepository,
                                           RecordHistoryRepository recordHistoryRepository) {
        this.oohCommandRepository = oohCommandRepository;
        this.recordHistoryRepository = recordHistoryRepository;
    }

    public void saveRecordHistory(Long userId, LocalDate date) {
        int postCount = oohCommandRepository.countByUserIdAndDate(userId, date);

        RecordHistory rh = new RecordHistory();
        rh.setUserId(userId);
        rh.setCountDate(date.atStartOfDay()); // LocalDateTime
        rh.setRecordType("H");
        rh.setRecordCount(postCount);
        recordHistoryRepository.save(rh);
    }
}
