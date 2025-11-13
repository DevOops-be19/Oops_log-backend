package com.devoops.oopslog.report.command.service;

import com.devoops.oopslog.comments.command.entity.Comments;
import com.devoops.oopslog.comments.command.repository.CommentsRepository;
import com.devoops.oopslog.member.command.entity.Member;
import com.devoops.oopslog.member.command.repository.MemberCommandRepository;
import com.devoops.oopslog.ooh.command.entity.OohCommandEntity;
import com.devoops.oopslog.ooh.command.repository.OohCommandRepository;
import com.devoops.oopslog.oops.command.entity.OopsCommandEntity;
import com.devoops.oopslog.oops.command.repository.OopsCommandRepository;
import com.devoops.oopslog.report.command.dto.ReportRequestDTO;
import com.devoops.oopslog.report.command.entity.ReportCategoryEntity;
import com.devoops.oopslog.report.command.entity.ReportEntity;
import com.devoops.oopslog.report.command.repository.ReportCategoryRepository;
import com.devoops.oopslog.report.command.repository.ReportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReportServiceImplTest {

    private ReportRepository reportRepository;
    private MemberCommandRepository memberCommandRepository;
    private ReportCategoryRepository reportCategoryRepository;
    private OohCommandRepository oohCommandRepository;
    private OopsCommandRepository oopsCommandRepository;
    private CommentsRepository commentsRepository;
    private ReportServiceImpl reportServiceImpl;

    @BeforeEach
    void setUp() {
        reportRepository = mock(ReportRepository.class);
        memberCommandRepository = mock(MemberCommandRepository.class);
        reportCategoryRepository = mock(ReportCategoryRepository.class);
        oohCommandRepository = mock(OohCommandRepository.class);
        oopsCommandRepository = mock(OopsCommandRepository.class);
        commentsRepository = mock(CommentsRepository.class);
        reportServiceImpl = new ReportServiceImpl(
                reportRepository, memberCommandRepository, reportCategoryRepository,
                oohCommandRepository, oopsCommandRepository, commentsRepository
        );
    }

    @Test
    @DisplayName("Ooh 게시글 신고 생성 성공")
    void testCreateReportWithOoh() {
        // given
        ReportRequestDTO dto = new ReportRequestDTO();
        dto.setCategoryId(1L);
        dto.setUserId(10L);
        dto.setOohId(100L);

        Member member = new Member();
        member.setId(10L);
        ReportCategoryEntity category = new ReportCategoryEntity();
        category.setId(1L);
        OohCommandEntity ooh = new OohCommandEntity();
        ooh.setOohId(100L);

        when(memberCommandRepository.findById(10L)).thenReturn(Optional.of(member));
        when(reportCategoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(oohCommandRepository.findById(100L)).thenReturn(Optional.of(ooh));
        when(reportRepository.existsByOohId(ooh)).thenReturn(false);
        when(reportRepository.save(any(ReportEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        ReportEntity result = reportServiceImpl.createReport(dto);

        // then
        assertNotNull(result);
        assertEquals(member, result.getUserId());
        assertEquals(category, result.getCategoryId());
        assertEquals(ooh, result.getOohId());
        assertEquals("U", result.getState());
        verify(reportRepository, times(1)).save(any(ReportEntity.class));
    }

    // -------------------------------------------------------
    @Test
    @DisplayName("이미 신고된 Oops 게시글은 예외 발생")
    void testCreateReportWithDuplicateOops() {
        ReportRequestDTO dto = new ReportRequestDTO();
        dto.setCategoryId(2L);
        dto.setUserId(11L);
        dto.setOopsId(200L);

        Member member = new Member();
        member.setId(11L);
        ReportCategoryEntity category = new ReportCategoryEntity();
        category.setId(2L);
        OopsCommandEntity oops = new OopsCommandEntity();
        oops.setOopsId(200L);

        when(memberCommandRepository.findById(11L)).thenReturn(Optional.of(member));
        when(reportCategoryRepository.findById(2L)).thenReturn(Optional.of(category));
        when(oopsCommandRepository.findById(200L)).thenReturn(Optional.of(oops));
        when(reportRepository.existsByOopsId(oops)).thenReturn(true); // 중복 신고 존재

        // when / then
        assertThrows(IllegalStateException.class, () -> reportServiceImpl.createReport(dto));
        verify(reportRepository, never()).save(any());
    }

    // -------------------------------------------------------
    @Test
    @DisplayName("신고 상태를 Y로 변경 시 대상이 블라인드 처리된다")
    void testUpdateReportStateToY() {
        // given
        ReportEntity report = new ReportEntity();
        report.setId(1L);
        report.setState("U");

        OopsCommandEntity oops = new OopsCommandEntity();
        oops.setOopsId(300L);
        report.setOopsId(oops);

        when(reportRepository.findById(1L)).thenReturn(Optional.of(report));

        // when
        reportServiceImpl.updateReportState(1L, "Y");

        // then
        assertEquals("Y", report.getState());
        assertEquals("Y", oops.getOopsIsDeleted());
        verify(oopsCommandRepository, times(1)).save(oops);
        verify(reportRepository, times(1)).save(report);
    }

    // -------------------------------------------------------
    @Test
    @DisplayName("이미 처리된 신고는 상태 변경 불가")
    void testUpdateReportStateAlreadyProcessed() {
        ReportEntity report = new ReportEntity();
        report.setId(2L);
        report.setState("Y"); // 이미 처리됨

        when(reportRepository.findById(2L)).thenReturn(Optional.of(report));

        assertThrows(IllegalStateException.class, () -> reportServiceImpl.updateReportState(2L, "N"));
        verify(reportRepository, never()).save(report);
    }

    // -------------------------------------------------------
    @Test
    @DisplayName("댓글 신고 승인 시 댓글이 블라인드 처리된다")
    void testUpdateReportStateToYForComment() {
        ReportEntity report = new ReportEntity();
        report.setId(3L);
        report.setState("U");

        Comments comment = new Comments();
        comment.setId(400L);
        comment.setIs_deleted("N");
        report.setCommentId(comment);

        when(reportRepository.findById(3L)).thenReturn(Optional.of(report));

        reportServiceImpl.updateReportState(3L, "Y");

        assertEquals("Y", report.getState());
        assertEquals("Y", comment.getIs_deleted());
        verify(commentsRepository, times(1)).save(comment);
        verify(reportRepository, times(1)).save(report);
    }
}
