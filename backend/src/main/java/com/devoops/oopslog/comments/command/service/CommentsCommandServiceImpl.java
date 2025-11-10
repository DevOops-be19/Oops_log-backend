package com.devoops.oopslog.comments.command.service;

import com.devoops.oopslog.comments.command.dto.CommentCommandDTO;
import com.devoops.oopslog.comments.command.entity.Comments;
import com.devoops.oopslog.comments.command.repository.CommentsRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CommentsCommandServiceImpl implements CommentsCommandService {
    private final CommentsRepository commentsRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CommentsCommandServiceImpl(CommentsRepository commentsRepository, ModelMapper modelMapper) {
        this.commentsRepository = commentsRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    @Transactional
    public String registOopsComment(CommentCommandDTO newComment, int oopsId, long userId) {
        Comments comments = modelMapper.map(newComment, Comments.class);
        comments.setId(userId);
        comments.setCreate_date(LocalDateTime.now());
        comments.setIs_deleted("N");
        comments.setOops_id((long)oopsId);

        commentsRepository.save(comments);
        return "oops comment write success";
    }

    @Override
    @Transactional
    public String registOohComment(CommentCommandDTO newComment, int oohId, long userId) {
        Comments comments = modelMapper.map(newComment, Comments.class);
        comments.setId(userId);
        comments.setCreate_date(LocalDateTime.now());
        comments.setIs_deleted("N");
        comments.setOoh_id((long)oohId);

        commentsRepository.save(comments);
        return "ooh comment write success";
    }

    @Override
    @Transactional
    public String registNoticeComment(CommentCommandDTO newComment, int noticeId, long userId) {
        Comments comments = modelMapper.map(newComment, Comments.class);
        comments.setId(userId);
        comments.setCreate_date(LocalDateTime.now());
        comments.setIs_deleted("N");
        comments.setNotice_id((long)noticeId);

        commentsRepository.save(comments);
        return "notice comment write success";
    }

    @Override
    @Transactional
    public String modifyComment(String content, int commentId) {
        Comments comment = commentsRepository.findById((long)commentId).get();
        comment.setContent(content);
        commentsRepository.save(comment);

        return "comment update success";
    }

    @Override
    @Transactional
    public String deleteComment(int commentId) {
        Comments comment = commentsRepository.findById((long)commentId).get();
        comment.setIs_deleted("Y");
        commentsRepository.save(comment);

        return "comment delete success";
    }
}
