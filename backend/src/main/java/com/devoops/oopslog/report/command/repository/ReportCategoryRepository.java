package com.devoops.oopslog.report.command.repository;

import com.devoops.oopslog.report.command.entity.ReportCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportCategoryRepository extends JpaRepository<ReportCategoryEntity,Long> {
}
