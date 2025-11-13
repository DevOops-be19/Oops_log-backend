package com.devoops.oopslog.comments.command.service;

import com.devoops.oopslog.comments.command.dto.CommentCommandDTO;
import com.devoops.oopslog.comments.command.entity.Comments;
import com.devoops.oopslog.comments.command.repository.CommentsRepository;
import com.devoops.oopslog.oops.command.entity.OopsCommandEntity;
import com.devoops.oopslog.oops.command.repository.OopsCommandRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class CommentsCommandServiceImpl implements CommentsCommandService {
    private final CommentsRepository commentsRepository;
    private final ModelMapper modelMapper;
    private final OopsCommandRepository oopsCommandRepository;

    @Autowired
    public CommentsCommandServiceImpl(CommentsRepository commentsRepository, ModelMapper modelMapper,
                                      OopsCommandRepository oopsCommandRepository) {
        this.commentsRepository = commentsRepository;
        this.modelMapper = modelMapper;
        this.oopsCommandRepository = oopsCommandRepository;
    }


    @Override
    @Transactional
    public String registOopsComment(CommentCommandDTO newComment, int oopsId, long userId) {
        Comments comments = modelMapper.map(newComment, Comments.class);
        log.info("comment 내용 추가: {}", comments);
        comments.setUser_id(userId);
        comments.setCreate_date(LocalDateTime.now());
        comments.setIs_deleted("N");
        comments.setOops_id((long)oopsId);

        commentsRepository.save(comments);

        OopsCommandEntity oopsCommandEntity =  oopsCommandRepository.findById((long)oopsId).get();

        return oopsCommandEntity.getOopsUserId()+"";
    }

    @Override
    @Transactional
    public String registOohComment(CommentCommandDTO newComment, int oohId, long userId) {
        Comments comments = modelMapper.map(newComment, Comments.class);
        comments.setUser_id(userId);
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
        comments.setUser_id(userId);
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

    @Override
    public String hardDeleteComment(int commentId) {
        Comments comment = commentsRepository.findById((long)commentId).get();
        commentsRepository.delete(comment);

        return "comment delete success";
    }
}
