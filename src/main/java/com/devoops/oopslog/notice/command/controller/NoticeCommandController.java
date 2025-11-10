package com.devoops.oopslog.notice.command.controller;

import com.devoops.oopslog.notice.command.dto.NoticeCommandCreateDTO;
import com.devoops.oopslog.notice.command.dto.NoticeCommandResponseDTO;
import com.devoops.oopslog.notice.command.service.NoticeCommandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/notice")
public class NoticeCommandController {
    private final NoticeCommandService noticeCommandService;

    @Autowired
    public NoticeCommandController(NoticeCommandService noticeCommandService) {
        this.noticeCommandService = noticeCommandService;
    }

    @PostMapping("/insertNotice")
    public ResponseEntity<NoticeCommandCreateDTO> insertNotice(
            @RequestBody NoticeCommandCreateDTO noticeCommandCreateDTO) {
        NoticeCommandCreateDTO saved
                = noticeCommandService.insertNotice(noticeCommandCreateDTO);

        if (saved == null) {
            log.info("공지사항 생성 실패: {}", noticeCommandCreateDTO);
            throw new IllegalArgumentException("공지사항 생성의 실패했습니다.");
        } else {
            log.info("공지사항 생성 완료!: {}", saved);
            return ResponseEntity.ok().body(saved);
        }
    }

    @PutMapping("/updateNotice/{id}")
    public ResponseEntity<NoticeCommandResponseDTO> updateNotice(
            // 회원 확인 할라면 PathVariable 사용 @PutMapping("/updateOoh/{id}") 여기의 id 가져올수 있
            @PathVariable Long id,
            @RequestBody NoticeCommandResponseDTO noticeCommandResponseDTO) {
        noticeCommandResponseDTO.setNoticeId(id);
        NoticeCommandResponseDTO updated = noticeCommandService.updateNotice(id, noticeCommandResponseDTO);

        log.info("공지사항 수정 완료: id={}, title={}", updated.getNoticeId(), updated.getNoticeContent());
        if (updated == null) {
            log.info("공지사항 수정 실패: {}", noticeCommandResponseDTO);
            throw new IllegalArgumentException("공지사항 수정의 실패했습니다.");
        } else {
            log.info("공지사항 수정 완료!: {}", updated);
            return ResponseEntity.ok(updated);
        }
    }

    @DeleteMapping("/deleteNotice/{id}")
    public ResponseEntity<Void> deleteNotice (@PathVariable Long id) {
        noticeCommandService.deleteNotice(id); // 소프트 삭제 권장
        log.info("공지사항 삭제(소프트) 완료: id={}", id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/hardDeleteNotice/{id}")
    public ResponseEntity<Void> hardDeleteNotice (@PathVariable Long id) {
        noticeCommandService.hardDeleteNotice(id);
        log.info("공지사항 하드 삭제 완료: id={}", id);
        return ResponseEntity.noContent().build(); // 204
    }
}
