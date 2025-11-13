package com.devoops.oopslog.admin.command.controller;

import com.devoops.oopslog.admin.command.dto.AdminTagRequestDTO;
import com.devoops.oopslog.admin.command.service.AdminService;
import com.devoops.oopslog.member.command.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/tag")
    public ResponseEntity<String> createTag(
            @RequestBody AdminTagRequestDTO dto
            ){
        adminService.addTag(dto.getTagName(), dto.getTagType());
        return ResponseEntity.ok().body("태그가 추가되었습니다");
    }

    @DeleteMapping("/tag/{tagId}")
    public ResponseEntity<String> deleteTag(
            @PathVariable Long tagId
    ){
        adminService.deleteTag(tagId);
        return ResponseEntity.ok("태그가 삭제되었습니다. ID: " + tagId);
    }

    // 회원 상태 변경
    @PatchMapping("/member/{memberId}/state")
    public ResponseEntity<?> updateMemberState(@PathVariable Long memberId,
                                               @RequestParam Character state) {
        Member updated = adminService.updateStateMember(memberId, state);
        return ResponseEntity.ok().body(
                String.format("회원 상태가 변경되었습니다. (ID: %d, newState: %s)",
                        updated.getId(), updated.getUser_state())
        );
    }
}
