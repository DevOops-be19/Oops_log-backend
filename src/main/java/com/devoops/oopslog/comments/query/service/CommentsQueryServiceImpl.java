package com.devoops.oopslog.comments.query.service;

import com.devoops.oopslog.comments.query.dto.CommentsQueryDTO;
import com.devoops.oopslog.comments.query.mapper.CommentsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentsQueryServiceImpl implements CommentsQueryService {
    private final CommentsMapper commentsMapper;

    @Autowired
    public CommentsQueryServiceImpl(CommentsMapper commentsMapper) {
        this.commentsMapper = commentsMapper;
    }

    @Override
    public List<CommentsQueryDTO> getCommentsByOohId(int oohId) {
        List<CommentsQueryDTO> commentsList = commentsMapper.selectCommentsByOohId(oohId);

        return commentsList;
    }

    @Override
    public List<CommentsQueryDTO> getCommentsByOopsId(int oopsId) {
        List<CommentsQueryDTO> commentsList = commentsMapper.selectCommentsByOopsId(oopsId);

        return commentsList;
    }

    @Override
    public List<CommentsQueryDTO> getCommentsByNoticeId(int noticeId) {
        List<CommentsQueryDTO> commentsList = commentsMapper.selectCommentsByNoticeId(noticeId);

        return commentsList;
    }
}
