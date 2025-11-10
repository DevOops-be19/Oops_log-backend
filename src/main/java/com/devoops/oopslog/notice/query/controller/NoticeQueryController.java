package com.devoops.oopslog.notice.query.controller;

import com.devoops.oopslog.notice.query.dto.NoticeQueryDTO;
import com.devoops.oopslog.notice.query.dto.NoticeQueryScrollResponseDTO;
import com.devoops.oopslog.notice.query.service.NoticeQueryService;
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
@RequestMapping("/notice")
public class NoticeQueryController {
     private final NoticeQueryService noticeQueryService;

    @Autowired
    public NoticeQueryController(NoticeQueryService noticeQueryService) {
        this.noticeQueryService = noticeQueryService;
    }

    // 무한 스크롤 + 검색 기반 조회
    @GetMapping("/all")
    public ResponseEntity<NoticeQueryScrollResponseDTO> selectNoticeList(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String content,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {


        List<NoticeQueryDTO> noticeList = noticeQueryService.selectNoticeList(title, content, page, size);
        int totalCount = noticeQueryService.selectNoticeCount(title, content);

        boolean hasNextPage = (page * size) < totalCount;

        NoticeQueryScrollResponseDTO response = new NoticeQueryScrollResponseDTO(noticeList, hasNextPage, totalCount);

        log.info("공지사항 조회 요청: title='{}', content='{}', page={}, size={}, totalCount={}, hasNextPage={}",
                title, content, page, size, totalCount, hasNextPage);

        if (page < 1) {
            throw new IllegalArgumentException("page는 1 이상이어야 합니다.");
        }

        return ResponseEntity.ok(response);
    }

//    @GetMapping("/{currentPage}")
//    public ResponseEntity<List<NoticeQueryDTO>> getNoticeListPage(){
//    return null;
//    }

//    @GetMapping("/{noticeId}")
//    public ResponseEntity<NoticeQueryDTO> getNoticeById()

}
