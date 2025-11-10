package com.devoops.oopslog.report.command.repository;

import com.devoops.oopslog.comments.command.entity.Comments;
import com.devoops.oopslog.ooh.command.entity.OohCommandEntity;
import com.devoops.oopslog.oops.command.entity.OopsCommandEntity;
import com.devoops.oopslog.report.command.entity.ReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<ReportEntity, Long>{
    // 동일한 신고글 신고 불가
    boolean existsByOohId(OohCommandEntity ooh);

    boolean existsByOopsId(OopsCommandEntity oops);

    boolean existsByCommentId(Comments comment);
}
