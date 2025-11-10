package com.devoops.oopslog.member.command.repository;

import com.devoops.oopslog.member.command.entity.LoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginHistoryCommandRepository extends JpaRepository<LoginHistory,Long> {
}
