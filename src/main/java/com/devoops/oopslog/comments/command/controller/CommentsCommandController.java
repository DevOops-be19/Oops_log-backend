package com.devoops.oopslog.comments.command.controller;

import com.devoops.oopslog.comments.command.dto.CommentCommandDTO;
import com.devoops.oopslog.comments.command.service.CommentsCommandService;
import com.devoops.oopslog.member.command.dto.UserImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
public class CommentsCommandController {
    private final CommentsCommandService commentsCommandService;

    @Autowired
    public CommentsCommandController(CommentsCommandService commentsCommandService) {
        this.commentsCommandService = commentsCommandService;
    }

    @PostMapping("/oops-insert/{oops_id}")
    public String writeCommentAtOops(@RequestBody CommentCommandDTO newComment,
                                     @PathVariable int oops_id){
//                                     , HttpServletRequest request) {
//        UserInfo userInfo = (UserInfo) request.getAttribute("userInfo");
//        long userId = userInfo.getId();
        long userId = 20;   // 임시값 지정
        String result = commentsCommandService.registOopsComment(newComment, oops_id, userId);

        return result;
    }

    @PostMapping("/ooh-insert/{ooh_id}")
    public String writeCommentAtOoh(@RequestBody CommentCommandDTO newComment,
                                     @PathVariable int ooh_id){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication.getPrincipal() instanceof UserImpl)){
            throw new RuntimeException("잘못된 id");
        }
        UserImpl userImpl = (UserImpl)authentication.getPrincipal();


        long userId = userImpl.getId();   // 임시값 지정
        String result = commentsCommandService.registOohComment(newComment, ooh_id, userId);

        return result;
    }

    @PostMapping("/notice-insert/{notice_id}")
    public String writeCommentAtNotice(@RequestBody CommentCommandDTO newComment,
                                     @PathVariable int notice_id){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication.getPrincipal() instanceof UserImpl)){
            throw new RuntimeException("잘못된 id");
        }
        UserImpl userImpl = (UserImpl)authentication.getPrincipal();

        String result = commentsCommandService.registNoticeComment(newComment, notice_id, userImpl.getId());

        return result;
    }

    @PutMapping("/update-comment/{comment_id}")
    public String updateComment(@RequestBody String content, @PathVariable int comment_id){
        String result = commentsCommandService.modifyComment(content, comment_id);

        return result;
    }

    @PutMapping("/delete-comment/{comment_id}")
    public String deleteComment(@PathVariable int comment_id){
        String result = commentsCommandService.deleteComment(comment_id);

        return result;
    }
}
