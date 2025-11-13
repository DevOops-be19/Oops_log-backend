package com.devoops.oopslog.member.command.controller;


import com.devoops.oopslog.member.command.dto.*;
import com.devoops.oopslog.member.command.service.MemberCommandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
@Slf4j
public class MemberCommandController {
    private final MemberCommandService memberCommandService;

    public MemberCommandController(MemberCommandService memberCommandService) {
        this.memberCommandService = memberCommandService;
    }

    @GetMapping("/health")
    public String health() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication instanceof UserImpl) {
//            UserImpl user = (UserImpl) authentication.getPrincipal();
//        }
//        log.info("health {}------------------", authentication.toString());
        return "I'm OK.";
    }

//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        return ResponseEntity.ok().body(authentication.getPrincipal().toString());
//    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody SignUpDTO signUpDTO) {
        memberCommandService.signUpMember(signUpDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("verify-pw")
    public ResponseEntity<?> verifyPw(@RequestBody VerifyPwDTO verifyPwDTO) {
        TemporaryPwResponseDTO temporaryPwResponseDTO = memberCommandService.verifyPw(verifyPwDTO);
        return  ResponseEntity.ok().body(temporaryPwResponseDTO);
    }

    @PutMapping("/modify")
    public ResponseEntity<?> modifyMemberInfo(@RequestBody ModifyDTO modifyDTO){
        memberCommandService.modifyMemberInfo(modifyDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/stop/{id}")
    public ResponseEntity<?> stopMember(@PathVariable Long id){
        memberCommandService.stopMember(id);
        return ResponseEntity.ok().build();
    }

}
