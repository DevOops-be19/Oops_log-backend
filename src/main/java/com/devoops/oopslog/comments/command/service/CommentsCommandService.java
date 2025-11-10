package com.devoops.oopslog.comments.command.service;

import com.devoops.oopslog.comments.command.dto.CommentCommandDTO;

public interface CommentsCommandService {
    String registOopsComment(CommentCommandDTO newComment, int oopsId, long userId);

    String registOohComment(CommentCommandDTO newComment, int oohId, long userId);

    String registNoticeComment(CommentCommandDTO newComment, int noticeId, long userId);

    String modifyComment(String content, int commentId);

    String deleteComment(int commentId);
}
