package com.devoops.oopslog.comments;

import com.devoops.oopslog.comments.command.dto.CommentCommandDTO;
import com.devoops.oopslog.comments.command.service.CommentsCommandService;
import com.devoops.oopslog.comments.query.dto.CommentsQueryDTO;
import com.devoops.oopslog.comments.query.service.CommentsQueryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
@Rollback
public class CommentsServiceTest {
    private CommentsQueryService commentsQueryService;
    private CommentsCommandService commentsCommandService;

    @Autowired
    public CommentsServiceTest(CommentsQueryService commentsQueryService,
                               CommentsCommandService commentsCommandService) {
        this.commentsQueryService = commentsQueryService;
        this.commentsCommandService = commentsCommandService;
    }

    @DisplayName("ooh기록 댓글 조회 테스트")
    @Test
    void commentOohReadServiceTest() {
        Assertions.assertDoesNotThrow(
                () -> {
                    List<CommentsQueryDTO> commentsList = commentsQueryService.getCommentsByOohId(10);
                    commentsList.forEach(System.out::println);
                });
    }

    @DisplayName("oops기록 댓글 조회 테스트")
    @Test
    void commentOopsReadServiceTest() {
        Assertions.assertDoesNotThrow(
                () -> {
                    List<CommentsQueryDTO> commentsList = commentsQueryService.getCommentsByOopsId(10);
                    commentsList.forEach(System.out::println);
                });
    }

    @DisplayName("Notice기록 댓글 조회 테스트")
    @Test
    void commentNoticeReadServiceTest() {
        Assertions.assertDoesNotThrow(
                () -> {
                    List<CommentsQueryDTO> commentsList = commentsQueryService.getCommentsByNoticeId(10);
                    commentsList.forEach(System.out::println);
                });
    }

    @DisplayName("댓글 등록 테스트 - ooh기록에 댓글 등록")
    @Test
    void commentOohRegistServiceTest() {
        Assertions.assertDoesNotThrow(
                () -> {
                    CommentCommandDTO newComment = new CommentCommandDTO("hello world");
                    String isCommentRegist = commentsCommandService.registOohComment(newComment, 102, 48);
                    System.out.println(isCommentRegist);
                }
        );
    }

    @DisplayName("댓글 등록 테스트 - oops기록에 댓글 등록")
    @Test
    void commentOopsRegistServiceTest() {
        Assertions.assertDoesNotThrow(
                () -> {
                    CommentCommandDTO newComment = new CommentCommandDTO("hello world");
                    String isCommentRegist = commentsCommandService.registOopsComment(newComment, 10, 48);
                    System.out.println(isCommentRegist);
                }
        );
    }

    @DisplayName("댓글 등록 테스트 - Notice기록에 댓글 등록")
    @Test
    void commentNoticeRegistServiceTest() {
        Assertions.assertDoesNotThrow(
                () -> {
                    CommentCommandDTO newComment = new CommentCommandDTO("hello world");
                    String isCommentRegist = commentsCommandService.registNoticeComment(newComment, 10, 48);
                    System.out.println(isCommentRegist);
                }
        );
    }

    @DisplayName("댓글 삭제 테스트")
    @Test
    void commendDeleteServiceTest() {
        Assertions.assertDoesNotThrow(
                () -> {
                    String isCommentDelete = commentsCommandService.deleteComment(104);
                    System.out.println("댓글 삭제 여부" + isCommentDelete);
                }
        );
    }
}
