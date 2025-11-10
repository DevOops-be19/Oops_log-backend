package com.devoops.oopslog.admin.command.controller;

import com.devoops.oopslog.admin.command.dto.TagRequestDTO;
import com.devoops.oopslog.admin.command.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping("/tag")
    public ResponseEntity<String> createTag(
            @RequestBody TagRequestDTO dto
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
