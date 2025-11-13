package com.devoops.oopslog.report.query.service;

import com.devoops.oopslog.report.query.dto.AllReportDTO;
import com.devoops.oopslog.report.query.dto.ReportDetailDTO;
import com.devoops.oopslog.report.query.mapper.ReportReadMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReportReadServiceImplTest {

    private ReportReadMapper reportReadMapper;
    private ReportReadServiceImpl reportReadServiceImpl;

    @BeforeEach
    void setUp() {
        // Mock 객체 생성
        reportReadMapper = Mockito.mock(ReportReadMapper.class);
        // Service 주입
        reportReadServiceImpl = new ReportReadServiceImpl(reportReadMapper);
    }

    @Test
    @DisplayName("전체 신고 목록을 페이징하여 조회한다")
    void testGetAllReport() {
        // given
        int page = 2;
        int size = 10;
        int expectedOffset = (page - 1) * size;

        List<AllReportDTO> mockList = Arrays.asList(
          new AllReportDTO(1L, new Date(), "U", "스팸", 101L, 201L, null, null),
          new AllReportDTO(2L, new Date(), "Y", "욕설", 102L, null, null, 303L)
        );

        when(reportReadMapper.selectAllReport(size, expectedOffset)).thenReturn(mockList);

        // when
        List<AllReportDTO> result = reportReadServiceImpl.getAllReport(page, size);

        // then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("U", result.get(0).getState());
        assertEquals(101L, result.get(0).getUser_id());
        verify(reportReadMapper, times(1)).selectAllReport(size, expectedOffset);
    }

    @Test
    @DisplayName("신고 상세 정보를 ID로 조회한다")
    void testGetReportDetailById() {
        // given
        Long reportId = 99L;
        ReportDetailDTO mockDetail = new ReportDetailDTO();
        mockDetail.setId(reportId);
        mockDetail.setState("U");
        mockDetail.setUser_id(100L);
        mockDetail.setTarget_id(200L);
        mockDetail.setTarget_content("게시글 내용");

        when(reportReadMapper.selectReportDetail(reportId)).thenReturn(mockDetail);

        // when
        ReportDetailDTO result = reportReadServiceImpl.getReportDetailById(reportId);

        // then
        assertNotNull(result);
        assertEquals(reportId, result.getId());
        assertEquals("U", result.getState());
        assertEquals(100L, result.getUser_id());
        assertEquals(200L, result.getTarget_id());
        assertEquals("게시글 내용", result.getTarget_content());
        verify(reportReadMapper, times(1)).selectReportDetail(reportId);
    }
}
