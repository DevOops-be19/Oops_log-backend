package com.devoops.oopslog.comments.query.service;

import com.devoops.oopslog.comments.query.dto.CommentsQueryDTO;

import java.util.List;

public interface CommentsQueryService {
    List<CommentsQueryDTO> getCommentsByOohId(int oohId);

    List<CommentsQueryDTO> getCommentsByOopsId(int oopsId);

    List<CommentsQueryDTO> getCommentsByNoticeId(int noticeId);
}
