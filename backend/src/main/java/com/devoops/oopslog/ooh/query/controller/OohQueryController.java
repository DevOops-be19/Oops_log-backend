package com.devoops.oopslog.ooh.query.controller;

import com.devoops.oopslog.ooh.query.dto.OohQueryDTO;
import com.devoops.oopslog.ooh.query.dto.OohQueryScrollResponseDTO;
import com.devoops.oopslog.ooh.query.service.OohQueryService;
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
@RequestMapping("/ooh")
public class OohQueryController {
    private final OohQueryService oohQueryService;

    @Autowired
    public OohQueryController(OohQueryService oohQueryService) {
        this.oohQueryService = oohQueryService;
    }
    // 무한 스크롤 + 검색 기반 조회
    @GetMapping("/all")
    public ResponseEntity<OohQueryScrollResponseDTO> selectOohList(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String content,
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

        // Service 호출 → MyBatis 쿼리 실행 → DTO 리스트 반환
        List<OohQueryDTO> OohList = oohQueryService.selectOohList(title, content, name, page, size);

        // 전체 개수 조회 (페이지네이션 계산용)
        int totalCount = oohQueryService.selectOohCount(title, name, content);

        // 다음 페이지 존재 여부 계산
        boolean hasNextPage = (page * size) < totalCount;

        OohQueryScrollResponseDTO response = new  OohQueryScrollResponseDTO(OohList, hasNextPage, totalCount);

        log.info("Ooh 기록 조회 요청: title='{}', content='{}', page={}, size={}, totalCount={}, hasNextPage={}",
                title, content, page, size, totalCount, hasNextPage);

        if (page < 1) {
            throw new IllegalArgumentException("page는 1 이상이어야 합니다.");
        }

        return ResponseEntity.ok(response);
    }


}
