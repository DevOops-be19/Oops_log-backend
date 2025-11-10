package com.devoops.oopslog.likes.query.controller;

import com.devoops.oopslog.likes.query.service.LikesQueryService;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/likes")
public class LikesQueryController {
    private final LikesQueryService likesQueryService;

    @Autowired
    public LikesQueryController(LikesQueryService likesQueryService) {
        this.likesQueryService = likesQueryService;
    }

    @GetMapping("/{oops_id}/oopslikes-count")
    public int checkOopsLikesCount(@PathVariable int oops_id) {
        int count = likesQueryService.oopsLikesCount(oops_id);

        return count;
    }

    @GetMapping("/{ooh_id}/oohlikes-count")
    public int checkOohLikesCount(@PathVariable int ooh_id) {
        int count = likesQueryService.oohLikesCount(ooh_id);

        return count;
    }

}
