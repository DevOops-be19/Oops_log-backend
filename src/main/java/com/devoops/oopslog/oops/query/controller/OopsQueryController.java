package com.devoops.oopslog.oops.query.controller;

import com.devoops.oopslog.oops.query.dto.*;
import com.devoops.oopslog.oops.query.service.OopsDetailService;
import com.devoops.oopslog.oops.query.service.OopsQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/oops")
public class OopsQueryController {
    private final OopsQueryService oopsQueryService;
    private final OopsDetailService oopsDetailService;

    @Autowired
    public OopsQueryController(OopsQueryService oopsQueryService, OopsDetailService oopsDetailService) {
        this.oopsQueryService = oopsQueryService;
        this.oopsDetailService = oopsDetailService;
    }
    // 무한 스크롤 + 검색 기반 조회
    @GetMapping("/all")
    public ResponseEntity<OopsQueryScrollResponseDTO> selectOopsList(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String content,
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

        // Service 호출 → MyBatis 쿼리 실행 → DTO 리스트 반환
        List<OopsQueryDTO> OopsList = oopsQueryService.selectOopsList(title, content, name, page, size);

        // 전체 개수 조회 (페이지네이션 계산용)
        int totalCount = oopsQueryService.selectOopsCount(title, name, content);

        // 다음 페이지 존재 여부 계산
        boolean hasNextPage = (page * size) < totalCount;

        OopsQueryScrollResponseDTO response = new  OopsQueryScrollResponseDTO(OopsList, hasNextPage, totalCount);

        log.info("Oops 기록 조회 요청: title='{}', content='{}', page={}, size={}, totalCount={}, hasNextPage={}",
                title, content, page, size, totalCount, hasNextPage);

        if (page < 1) {
            throw new IllegalArgumentException("page는 1 이상이어야 합니다.");
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{oops_id}")
    public ResponseEntity<OopsQuerySelectDTO> selectOopsRecordById(@PathVariable Long oops_id) {
        OopsQuerySelectDTO oopsRecord = oopsQueryService.selectOopsById(oops_id);

        return ResponseEntity.ok().body(oopsRecord);
    }

    @GetMapping("/{oopsId}/detail")
    public ResponseEntity<OopsDetailDTO> getDetail(
            @PathVariable Long oopsId,
            @RequestParam(defaultValue = "10") int commentLimit
            /*, @AuthenticationPrincipal JwtUser me */) {

        OopsDetailDTO dto = oopsDetailService.getDetail(oopsId, commentLimit /*, me != null ? me.getId() : null*/);
        if (dto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}/mypage")
    public ResponseEntity<List<OopsMemById>> selectOopsMemById(@PathVariable Long id) {
        List<OopsMemById> result = oopsQueryService.selectOopsRecordByMemId(id);
        return ResponseEntity.ok().body(result);
    }

}
