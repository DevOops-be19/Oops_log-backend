package com.devoops.oopslog.tag.command.controller;

import com.devoops.oopslog.tag.command.dto.TagCommandDTO;
import com.devoops.oopslog.tag.command.service.TagCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tag")
public class TagCommandController {
    private final TagCommandService tagCommandService;

    @Autowired
    public TagCommandController(TagCommandService tagCommandService) {
        this.tagCommandService = tagCommandService;
    }

    @GetMapping("/{tag_id}/{oops_id}/oopstag-used")
    public String oopsTagUsed(@PathVariable Long tag_id, @PathVariable Long oops_id) {
        tagCommandService.oopsTagUsed(tag_id, oops_id);

        return "success";
    }

    @GetMapping("/{tag_id}/{ooh_id}/oohtag-used")
    public String oohTagUsed(@PathVariable Long tag_id, @PathVariable Long ooh_id) {
        tagCommandService.oohTagUsed(tag_id, ooh_id);

        return "success";
    }

    @PostMapping("/tag-insert")
    public String createTag(@RequestBody TagCommandDTO tagDTO){
        String result = tagCommandService.tagCreate(tagDTO);

        return result;
    }

    @DeleteMapping("/tag-delete/{tag_id}")
    public String deleteTag(@PathVariable Long tag_id){
        String result = tagCommandService.deleteTagById(tag_id);

        return result;
    }

    // ooh기록 삭제 시 사용된 태그도 같이 삭제되도록 요청보낼 것!
    @DeleteMapping("/oohtag-delete/{tag_id}/{ooh_id}")
    public String deleteUsedOohTag(@PathVariable Long tag_id, @PathVariable Long ooh_id){
        String result = tagCommandService.deleteUsedOohTag(tag_id, ooh_id);

        return result;
    }

    // oops기록 삭제 시 사용된 태그도 같이 삭제되도록 요청보낼 것!
    @DeleteMapping("/oopstag-delete/{tag_id}/{oops_id}")
    public String deleteUsedOopsTag(@PathVariable Long tag_id, @PathVariable Long oops_id){
        String result = tagCommandService.deleteUsedOopsTag(tag_id, oops_id);

        return result;
    }

    @PutMapping("/tag-modify/{tag_id}")
    public String modifyTag(@PathVariable Long tag_id, @RequestBody TagCommandDTO tagDTO){
        String result = tagCommandService.modifyTagById(tag_id, tagDTO);
        return result;
    }
}
