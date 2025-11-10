package com.devoops.oopslog.ooh.command.controller;

import com.devoops.oopslog.ooh.command.dto.OohCommandCreateDTO;
import com.devoops.oopslog.ooh.command.dto.OohCommandResponseDTO;
import com.devoops.oopslog.ooh.command.service.OohCommandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ooh")
@Slf4j
public class OohCommandController {
    private final OohCommandService oohCommandService;

    @Autowired
    public OohCommandController(OohCommandService oohCommandService) {
        this.oohCommandService = oohCommandService;
    }

    @PostMapping("/insertOoh")
    public ResponseEntity<OohCommandCreateDTO> insertOoh(
            @RequestBody OohCommandCreateDTO oohCommandCreateDTO) {

        // insert 할 DTO들 저장하는것
        OohCommandCreateDTO saved
                = oohCommandService.insertOoh(oohCommandCreateDTO);

        if (saved == null) {
            log.info("Ooh 기록 생성 실패: {}", oohCommandCreateDTO);
            throw new IllegalArgumentException("Ooh 기록 생성의 실패했습니다.");
        } else {
            log.info("Ooh 기록 생성 완료!: {}", saved);
            return ResponseEntity.ok().body(saved);
        }
    }

    @PutMapping("/updateOoh/{id}")
    public ResponseEntity<OohCommandResponseDTO> updateOoh(
            // 회원 확인 할라면 PathVariable 사용 @PutMapping("/updateOoh/{id}") 여기의 id 가져올수 있음
            @PathVariable Long id,
            @RequestBody OohCommandResponseDTO oohCommandResponseDTO) {
        oohCommandResponseDTO.setOohId(id);
        OohCommandResponseDTO updated = oohCommandService.updateOoh(id, oohCommandResponseDTO);

        log.info("Ooh 기록 수정 완료: id={}, title={}", updated.getOohId(), updated.getOohTitle());
        if (updated == null) {
            log.info("Ooh 기록 수정 실패: {}", oohCommandResponseDTO);
            throw new IllegalArgumentException("Ooh 기록 수정의 실패했습니다.");
        } else {
            log.info("Ooh 기록 수정 완료!: {}", updated);
            return ResponseEntity.ok(updated);
        }
    }

    @DeleteMapping("/deleteOoh/{id}")
    public ResponseEntity<Void> deleteOoh(@PathVariable Long id) {
        oohCommandService.deleteOoh(id); // 소프트 삭제 권장
        log.info("Ooh 기록 삭제(소프트) 완료: id={}", id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/hardDeleteOoh/{id}")
    public ResponseEntity<Void> hardDeleteOoh(@PathVariable Long id) {
        oohCommandService.hardDeleteOoh(id);
        log.info("Ooh 하드 삭제 완료: id={}", id);
        return ResponseEntity.noContent().build(); // 204
    }
}
