package com.devoops.oopslog.admin.command.controller;

import com.devoops.oopslog.admin.command.dto.AdminTagRequestDTO;
import com.devoops.oopslog.admin.command.service.AdminTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminTagController {

    private final AdminTagService tagService;

    @Autowired
    public AdminTagController(AdminTagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping("/tag")
    public ResponseEntity<String> createTag(
            @RequestBody AdminTagRequestDTO dto
            ){
        tagService.addTag(dto.getTagName(), dto.getTagType());
        return ResponseEntity.ok().body("태그가 추가되었습니다");
    }

    @DeleteMapping("/tag/{tagId}")
    public ResponseEntity<String> deleteTag(
            @PathVariable Long tagId
    ){
        tagService.deleteTag(tagId);
        return ResponseEntity.ok("태그가 삭제되었습니다. ID: " + tagId);
    }
}
