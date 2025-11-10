package com.devoops.oopslog.comments.command.service;

import com.devoops.oopslog.comments.command.dto.CommentCommandDTO;
import com.devoops.oopslog.comments.command.entity.Comments;
import com.devoops.oopslog.comments.command.repository.CommentsRepository;
import com.devoops.oopslog.common.SseService;
import com.devoops.oopslog.oops.command.entity.OopsCommandEntity;
import com.devoops.oopslog.oops.command.repository.OopsCommandRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
@Slf4j
public class CommentsCommandServiceImpl implements CommentsCommandService {
    private final CommentsRepository commentsRepository;
    private final ModelMapper modelMapper;
    private final OopsCommandRepository oopsCommandRepository;
    private final SseService sseService;

    @Autowired
    public CommentsCommandServiceImpl(CommentsRepository commentsRepository, ModelMapper modelMapper,
                                      OopsCommandRepository oopsCommandRepository,SseService sseService) {
        this.commentsRepository = commentsRepository;
        this.modelMapper = modelMapper;
        this.oopsCommandRepository = oopsCommandRepository;
        this.sseService = sseService;
    }


    @Override
    @Transactional
    public String registOopsComment(CommentCommandDTO newComment, int oopsId, long userId) {
        Comments comments = modelMapper.map(newComment, Comments.class);
        comments.setUser_id(userId);
        comments.setCreate_date(LocalDateTime.now());
        comments.setIs_deleted("N");
        comments.setOops_id((long)oopsId);

        commentsRepository.save(comments);

        // 댓글 달리면 sse로 이벤트 전송
        OopsCommandEntity oopsCommandEntity = oopsCommandRepository.findById((long)oopsId).get();
        sseService.sseSend(oopsCommandEntity.getOopsUserId()," " + userId + "회원님이 메세지를 보냈습니다.");


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
