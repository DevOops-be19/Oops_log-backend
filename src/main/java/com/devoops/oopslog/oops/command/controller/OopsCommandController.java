package com.devoops.oopslog.oops.command.controller;

import com.devoops.oopslog.oops.command.dto.OopsCommandCreateDTO;
import com.devoops.oopslog.oops.command.dto.OopsCommandResponseDTO;
import com.devoops.oopslog.oops.command.service.OopsCommandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/oops")
@Slf4j
public class OopsCommandController {

    private final OopsCommandService oopsCommandService;

    @Autowired
    public OopsCommandController(OopsCommandService oopsCommandService) {
        this.oopsCommandService = oopsCommandService;
    }

    @PostMapping("/insertOops")
    public ResponseEntity<OopsCommandCreateDTO> insertOops(
            @RequestBody OopsCommandCreateDTO oopsCommandCreateDTO){
        OopsCommandCreateDTO saved = oopsCommandService.insertOops(oopsCommandCreateDTO);
        if (saved == null) {
            log.info("Oops 기록 생성 실패: {}", oopsCommandCreateDTO);
            throw new IllegalArgumentException("Ooh 기록 생성의 실패했습니다.");
        } else {
            log.info("Oops 기록 생성 완료!: {}", saved);
            return ResponseEntity.ok().body(saved);
        }
    }

    @PutMapping("/updateOops/{id}")
    public ResponseEntity<OopsCommandResponseDTO> updateOops(
            // 회원 확인 할라면 PathVariable 사용 @PutMapping("/updateOops/{id}") 여기의 id 가져올수 있음
            @PathVariable Long id,
            @RequestBody OopsCommandResponseDTO oopsCommandResponseDTO) {
        oopsCommandResponseDTO.setOopsId(id);
        OopsCommandResponseDTO updated = oopsCommandService.updateOops(id, oopsCommandResponseDTO);

        log.info("Oops 기록 수정 완료: id={}, title={}", updated.getOopsId(), updated.getOopsTitle());
        if (updated == null) {
            log.info("Oops 기록 수정 실패: {}", oopsCommandResponseDTO);
            throw new IllegalArgumentException("Oops 기록 수정의 실패했습니다.");
        } else {
            log.info("Ooh 기록 수정 완료!: {}", updated);
            return ResponseEntity.ok(updated);
        }
    }

    @DeleteMapping("/deleteOops/{id}")
    public ResponseEntity<Void> deleteOops(@PathVariable Long id) {
        oopsCommandService.deleteOops(id); // 소프트 삭제 권장
        log.info("Ooh 기록 삭제(소프트) 완료: id={}", id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/hardDeleteOops/{id}")
    public ResponseEntity<Void> hardDeleteOops(@PathVariable Long id) {
        oopsCommandService.hardDeleteOops(id);
        log.info("Ooh 하드 삭제 완료: id={}", id);
        return ResponseEntity.noContent().build(); // 204
    }


}
