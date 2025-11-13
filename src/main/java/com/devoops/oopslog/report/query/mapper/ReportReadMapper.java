package com.devoops.oopslog.report.query.mapper;

import com.devoops.oopslog.report.query.dto.AllReportCategoryDTO;
import com.devoops.oopslog.report.query.dto.AllReportDTO;
import com.devoops.oopslog.report.query.dto.ReportDetailDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReportReadMapper {
    List<AllReportDTO> selectAllReport(@Param("limit") int limit, @Param("offset") int offset);
    ReportDetailDTO selectReportDetail(@Param("reportId") Long reportId);
    List<AllReportCategoryDTO> selectReportCategory();
}
