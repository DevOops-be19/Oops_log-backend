package com.devoops.oopslog.recordhistory.command.service;

import com.devoops.oopslog.ooh.command.repository.OohCommandRepository;
import com.devoops.oopslog.oops.command.repository.OopsCommandRepository;
import com.devoops.oopslog.recordhistory.command.entity.RecordHistory;
import com.devoops.oopslog.recordhistory.command.repository.RecordHistoryRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class RecordHistoryCommandServiceImpl implements RecordHistoryCommandService {
    private final OohCommandRepository oohCommandRepository;
    private final RecordHistoryRepository recordHistoryRepository;
    private final OopsCommandRepository oopsCommandRepository;

    @Autowired
    public RecordHistoryCommandServiceImpl(OohCommandRepository oohCommandRepository,
                                           RecordHistoryRepository recordHistoryRepository,
                                           OopsCommandRepository oopsCommandRepository) {
        this.oohCommandRepository = oohCommandRepository;
        this.recordHistoryRepository = recordHistoryRepository;
        this.oopsCommandRepository = oopsCommandRepository;
    }

    @Override
    @Transactional
    public String saveOohRecordHistory(long userId) {

        List<Object[]> results = oohCommandRepository.countPostsByUserGroupByDate(userId);
        for (Object[] row : results) {
            java.sql.Date sqlDate = (java.sql.Date) row[0];
            LocalDate date = sqlDate.toLocalDate();
            Long count = ((Number) row[1]).longValue();
            LocalDateTime countDate = date.atStartOfDay();

            Optional<RecordHistory> optionalRecord =
                    recordHistoryRepository.findByUserIdAndCountDateAndRecordType(userId, countDate, "H");

            if (optionalRecord.isPresent()) {
                RecordHistory record = optionalRecord.get();
                if (record.getRecordCount() != count.intValue()) {
                    record.setRecordCount(count.intValue()); // update만
                    recordHistoryRepository.save(record);
                }
            } else {
                RecordHistory rh = new RecordHistory();
                rh.setUserId(userId);
                rh.setCountDate(countDate);
                rh.setRecordCount(count.intValue());
                rh.setRecordType("H");

                recordHistoryRepository.save(rh);
            }

        }
        return "success";
    }

    @Override
    @Transactional
    public String saveOopsRecordHistory(long userId) {
        List<Object[]> results = oopsCommandRepository.countPostsByUserGroupByDate(userId);
        for (Object[] row : results) {
            java.sql.Date sqlDate = (java.sql.Date) row[0];
            LocalDate date = sqlDate.toLocalDate();
            Long count = ((Number) row[1]).longValue();
            LocalDateTime countDate = date.atStartOfDay();

            Optional<RecordHistory> optionalRecord =
                    recordHistoryRepository.findByUserIdAndCountDateAndRecordType(userId, countDate, "P");

            if (optionalRecord.isPresent()) {
                RecordHistory record = optionalRecord.get();
                if (record.getRecordCount() != count.intValue()) {
                    record.setRecordCount(count.intValue()); // update만
                    recordHistoryRepository.save(record);
                }
            } else {
                RecordHistory rh = new RecordHistory();
                rh.setUserId(userId);
                rh.setCountDate(countDate);
                rh.setRecordCount(count.intValue());
                rh.setRecordType("P");

                recordHistoryRepository.save(rh);
            }

        }
        return "success";
    }
}

