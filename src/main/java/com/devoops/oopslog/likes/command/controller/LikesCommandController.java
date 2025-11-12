package com.devoops.oopslog.likes.command.controller;

import com.devoops.oopslog.likes.command.service.LikesCommandService;
import com.devoops.oopslog.member.command.dto.UserImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/likes")
public class LikesCommandController {
    private LikesCommandService likesCommandService;

    @Autowired
    public LikesCommandController(LikesCommandService likesCommandService) {
        this.likesCommandService = likesCommandService;
    }

    @GetMapping("/{oops_id}/oops-like")
    public String pushOopsLikes(@PathVariable int oops_id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication.getPrincipal() instanceof UserImpl)){
            throw new RuntimeException("잘못된 id");
        }
        UserImpl userImpl = (UserImpl)authentication.getPrincipal();


        long userId = userImpl.getId();

//        long userId = 20; // 임시값

        String result = likesCommandService.createOrDeleteLikesByOopsId(oops_id, userId);
        return result;
    }

    @GetMapping("/{ooh_id}/ooh-like")
    public String pushOohLikes(@PathVariable int ooh_id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication.getPrincipal() instanceof UserImpl)){
            throw new RuntimeException("잘못된 id");
        }
        UserImpl userImpl = (UserImpl)authentication.getPrincipal();


        long userId = userImpl.getId();

//        long userId = 20; // 임시값

        String result = likesCommandService.createOrDeleteLikesByOohId(ooh_id, userId);
        return result;
    }

    /* 설명. 게시글 조회 시 로그인사용자가 좋아요를 누른 게시물인지 확인(onmount()에 사용) */
    @GetMapping("/{oops_id}/oopslikes-exist")   // 넘어올 값: userId, oopsId
    public String checkOopsLikesExist(@PathVariable int oops_id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication.getPrincipal() instanceof UserImpl)){
            throw new RuntimeException("잘못된 id");
        }
        UserImpl userImpl = (UserImpl)authentication.getPrincipal();


        long userId = userImpl.getId();
//        long userId = 20;  // 임시값(로그인한 사용자ID 넘겨줄 것!)
        String result = likesCommandService.isOopsLikeExist(oops_id, userId);

        return result;
    }

    @GetMapping("/{ooh_id}/oohlikes-exist")   // 넘어올 값: userId, oopsId
    public String checkOohLikesExist(@PathVariable int ooh_id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication.getPrincipal() instanceof UserImpl)){
            throw new RuntimeException("잘못된 id");
        }
        UserImpl userImpl = (UserImpl)authentication.getPrincipal();


        long userId = userImpl.getId();
//        long userId = 20;
        String result = likesCommandService.isOohLikeExist(ooh_id, userId);

        return result;
    }
}
