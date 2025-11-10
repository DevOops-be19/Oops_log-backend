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
import com.devoops.oopslog.report.command.entity.*;
import com.devoops.oopslog.report.command.repository.*;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Transactional
public class ReportSerivceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final MemberCommandRepository memberRepository;
    private final ReportCategoryRepository reportCategoryRepository;
    private final OohCommandRepository oohCommandRepository;
    private final OopsCommandRepository oopsCommandRepository;
    private final CommentsRepository commentsRepository;

    @Autowired
    public ReportSerivceImpl(ReportRepository reportRepository,
                             MemberCommandRepository memberRepository,
                             ReportCategoryRepository reportCategoryRepository,
                             OohCommandRepository oohCommandRepository,
                             OopsCommandRepository oopsCommandRepository,
                             CommentsRepository commentsRepository) {
        this.reportRepository = reportRepository;
        this.memberRepository = memberRepository;
        this.reportCategoryRepository = reportCategoryRepository;
        this.oohCommandRepository = oohCommandRepository;
        this.oopsCommandRepository = oopsCommandRepository;
        this.commentsRepository = commentsRepository;
    }


    @Override
    public ReportEntity createReport(ReportRequestDTO dto) {
        // 필수 엔티티 조회
        Member user = memberRepository.findById(dto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));

        ReportCategoryEntity category = reportCategoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("신고 카테고리가 존재하지 않습니다."));

        // 빌더 패턴으로 ReportEntity 생성
        ReportEntity report = ReportEntity.builder()
                .reportDate(new Date())  // 현재 시각
                .state("U")              // 기본 상태: U (Unprocessed)
                .categoryId(category)
                .userId(user)
                .build();

        // 선택적 신고 대상 설정 (null 가능)
        if (dto.getOohId() != null) {
            OohCommandEntity ooh = oohCommandRepository.findById(dto.getOohId())
                    .orElseThrow(() -> new IllegalArgumentException("해당 Ooh 게시글이 존재하지 않습니다."));
            if (reportRepository.existsByOohId(ooh)) {
                throw new IllegalStateException("이미 신고된 게시글입니다.");
            }
            report.setOohId(ooh);
        }

        if (dto.getOopsId() != null) {
            OopsCommandEntity oops = oopsCommandRepository.findById(dto.getOopsId())
                    .orElseThrow(() -> new IllegalArgumentException("해당 Oops 게시글이 존재하지 않습니다."));
            if (reportRepository.existsByOopsId(oops)) {
                throw new IllegalStateException("이미 신고된 게시글입니다.");
            }
            report.setOopsId(oops);
        }

        if (dto.getCommentId() != null) {
            Comments comment = commentsRepository.findById(dto.getCommentId())
                    .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));
            if (reportRepository.existsByCommentId(comment)) {
                throw new IllegalStateException("이미 신고된 댓글입니다.");
            }
            report.setCommentId(comment);
        }

        return reportRepository.save(report);
    }

    @Override
    public void updateReportState(Long reportId, String state){
        ReportEntity report = reportRepository.findById(reportId)
                .orElseThrow(() -> new IllegalArgumentException("해당 신고가 존재하지 않습니다."));

        // 현재 상태가 U일 때만 변경 허용
        if (!"U".equals(report.getState())) {
            throw new IllegalStateException("이미 처리된 신고입니다.");
        }

        // 상태 변경
        report.setState(state);

        // 승인(Y) 시 대상 블라인드 처리
        if ("Y".equals(state)) {
            // oops 게시글일 경우
            if (report.getOopsId() != null) {
                OopsCommandEntity oops = report.getOopsId();
                oops.setOopsIsDeleted("Y");
                oopsCommandRepository.save(oops);
            }

            // ooh 게시글일 경우
            if (report.getOohId() != null) {
                OohCommandEntity ooh = report.getOohId();
                ooh.setOohIsDeleted("Y");
                oohCommandRepository.save(ooh);
            }

            // 댓글일 경우
            if (report.getCommentId() != null) {
                Comments comment = report.getCommentId();
                comment.setIs_deleted("Y");
                commentsRepository.save(comment);
            }
        }

        // 반려(N)은 아무 동작도 안 함
        reportRepository.save(report);
    }
}
