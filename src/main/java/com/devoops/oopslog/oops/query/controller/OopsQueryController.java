package com.devoops.oopslog.oops.query.controller;

import com.devoops.oopslog.oops.query.dto.OopsQueryDTO;
import com.devoops.oopslog.oops.query.dto.OopsQueryScrollResponseDTO;
import com.devoops.oopslog.oops.query.service.OopsQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/oops")
public class OopsQueryController {
    private final OopsQueryService oopsQueryService;

    @Autowired
    public OopsQueryController(OopsQueryService oopsQueryService) {
        this.oopsQueryService = oopsQueryService;
    }
    // 무한 스크롤 + 검색 기반 조회
    @GetMapping("/all")
    public ResponseEntity<OopsQueryScrollResponseDTO> selectOopsList(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String content,
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

        List<OopsQueryDTO> OopsList = oopsQueryService.selectOopsList(title, content, name, page, size);
        int totalCount = oopsQueryService.selectOopsCount(title, name, content);

        boolean hasNextPage = (page * size) < totalCount;

        OopsQueryScrollResponseDTO response = new  OopsQueryScrollResponseDTO(OopsList, hasNextPage, totalCount);

        log.info("Oops 기록 조회 요청: title='{}', content='{}', page={}, size={}, totalCount={}, hasNextPage={}",
                title, content, page, size, totalCount, hasNextPage);

        if (page < 1) {
            throw new IllegalArgumentException("page는 1 이상이어야 합니다.");
        }

        return ResponseEntity.ok(response);
    }


}
