package com.devoops.oopslog.comments.query.controller;

import com.devoops.oopslog.comments.query.dto.CommentsQueryDTO;
import com.devoops.oopslog.comments.query.service.CommentsQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentsQueryController {
    private final CommentsQueryService commentsQueryService;

    @Autowired
    public CommentsQueryController(CommentsQueryService commentsQueryService) {
        this.commentsQueryService = commentsQueryService;
    }

    @GetMapping("/ooh-read/{ooh_id}")
    public ResponseEntity<List<CommentsQueryDTO>> readOohComments(@PathVariable int ooh_id) {
        List<CommentsQueryDTO> commentsList = commentsQueryService.getCommentsByOohId(ooh_id);

        return ResponseEntity.ok().body(commentsList);
    }

    @GetMapping("/oops-read/{oops_id}")
    public ResponseEntity<List<CommentsQueryDTO>> readOopsComments(@PathVariable int oops_id) {
        List<CommentsQueryDTO> commentsList = commentsQueryService.getCommentsByOopsId(oops_id);

        return ResponseEntity.ok().body(commentsList);
    }

    @GetMapping("/notice-read/{notice_id}")
    public ResponseEntity<List<CommentsQueryDTO>> getNoticeComments(@PathVariable int notice_id) {
        List<CommentsQueryDTO> commentsList = commentsQueryService.getCommentsByNoticeId(notice_id);

        return ResponseEntity.ok().body(commentsList);
    }
}
