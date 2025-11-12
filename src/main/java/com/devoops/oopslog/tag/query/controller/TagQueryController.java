package com.devoops.oopslog.tag.query.controller;

import com.devoops.oopslog.tag.query.dto.TagNameDTO;
import com.devoops.oopslog.tag.query.service.TagQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tag")
@Slf4j
public class TagQueryController {
    private final TagQueryService tagQueryService;

    @Autowired
    public TagQueryController(TagQueryService tagQueryService) {
        this.tagQueryService = tagQueryService;
    }

    @GetMapping("/oopstag-read/{oops_id}")
    public ResponseEntity<List<TagNameDTO>> oopsTagRead(@PathVariable int oops_id) {
        List<TagNameDTO> tagNameList = tagQueryService.getOopsTagByOopsId(oops_id);

        return ResponseEntity.ok().body(tagNameList);
    }

    @GetMapping("/oohtag-read/{ooh_id}")
    public ResponseEntity<List<TagNameDTO>> oohTagRead(@PathVariable int ooh_id) {
        List<TagNameDTO> tagNameList = tagQueryService.getOohTagByOohId(ooh_id);

        return ResponseEntity.ok().body(tagNameList);
    }

    @GetMapping("/oopstag-select")
    public ResponseEntity<List<TagNameDTO>> oopsTagSelect() {
        List<TagNameDTO> tagNameList = tagQueryService.getOopsTag();

        log.info("태그 목록 조회(oops): {}", tagNameList);
        return ResponseEntity.ok().body(tagNameList);
    }

    @GetMapping("/oohtag-select")
    public ResponseEntity<List<TagNameDTO>> oohTagSelect() {
        List<TagNameDTO> tagNameList = tagQueryService.getOohTag();

        return ResponseEntity.ok().body(tagNameList);
    }
}
