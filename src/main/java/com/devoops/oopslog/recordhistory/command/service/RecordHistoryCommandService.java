package com.devoops.oopslog.recordhistory.command.service;

public interface RecordHistoryCommandService {
    String saveOohRecordHistory(long userId);

    String saveOopsRecordHistory(long userId);
}
