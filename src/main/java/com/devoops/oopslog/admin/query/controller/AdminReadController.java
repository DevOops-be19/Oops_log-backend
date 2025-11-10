package com.devoops.oopslog.admin.query.controller;

import com.devoops.oopslog.admin.query.dto.AllMemberDTO;
import com.devoops.oopslog.admin.query.dto.AllTagDTO;
import com.devoops.oopslog.admin.query.service.AdminReadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/admin")
public class AdminReadController {

    private final AdminReadService adminReadService;

    public AdminReadController(AdminReadService adminReadService) {
        this.adminReadService = adminReadService;
    }

    @GetMapping("/member")
    public ResponseEntity<List<AllMemberDTO>> getAllMember(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size
    ){
        List<AllMemberDTO> result = adminReadService.getAllMember(page, size);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/tag")
    public ResponseEntity<List<AllTagDTO>> getAllTag(){
        List<AllTagDTO> result = adminReadService.getAllTag();
        return ResponseEntity.ok().body(result);
    }
}
