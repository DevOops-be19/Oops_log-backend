package com.devoops.oopslog.recordhistory.command.repository;

import com.devoops.oopslog.recordhistory.command.entity.RecordHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface RecordHistoryRepository extends JpaRepository<RecordHistory, Long> {
    // 특정 유저의 특정 날짜 기록 존재하는지 조회 (날짜만 비교)
    Optional<RecordHistory> findByUserIdAndCountDate(Long userId, LocalDateTime countDate);

    Optional<RecordHistory> findByUserIdAndCountDateAndRecordType(long userId, LocalDateTime countDate, String recordType);
}
