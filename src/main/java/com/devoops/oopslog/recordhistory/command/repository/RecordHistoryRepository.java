package com.devoops.oopslog.recordhistory.command.repository;

import com.devoops.oopslog.recordhistory.command.entity.RecordHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordHistoryRepository extends JpaRepository<RecordHistory, Long> {
}
