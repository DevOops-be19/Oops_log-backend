package com.devoops.oopslog.member.query.controller;

import com.devoops.oopslog.common.SseService;
import com.devoops.oopslog.member.query.dto.FindPwDTO;
import com.devoops.oopslog.member.query.dto.FindPwResponseDTO;
import com.devoops.oopslog.member.query.dto.FindIdDTO;
import com.devoops.oopslog.member.query.service.MemberQueryService;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/member")
public class MemberQueryController {
    private final MemberQueryService memberQueryService;
    private final SseService sseService;

    public MemberQueryController(MemberQueryService memberQueryService,
                                 SseService sseService) {
        this.memberQueryService = memberQueryService;
        this.sseService = sseService;
    }

    @PostMapping("/find-id")
    public ResponseEntity<?> findMemberId(@RequestBody FindIdDTO findIdDTO){
        try {
            memberQueryService.findMemberId(findIdDTO);
        } catch (MessagingException | UnsupportedEncodingException e) {
            return ResponseEntity.badRequest().body("이메일 전송에 실패했습니다. - "+e.getMessage());
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping("/find-pw")
    public ResponseEntity<?> findMemberPw(@RequestBody FindPwDTO findPwDTO){
        FindPwResponseDTO findPwResponseDTO = null;
        try {
            findPwResponseDTO = memberQueryService.findMemberPw(findPwDTO);
        } catch (MessagingException | UnsupportedEncodingException e) {
            return ResponseEntity.badRequest().body("이메일 전송에 실패했습니다. - "+e.getMessage());
        }

        return ResponseEntity.ok().body(findPwResponseDTO);
    }

    @GetMapping("/sse-sub/{id}")
    public SseEmitter sseSubscribe(@PathVariable Long id){
        return sseService.sseSubscribe(id);
    }

    @GetMapping("sse-unsub/{id}")
    public void sseUnsubscribe(@PathVariable Long id){
        sseService.sseUnsubscribe(id);
    }

    @GetMapping("/ssetest/{id}/{msg}")
    public void ssetest(@PathVariable Long id,@PathVariable String msg){
        sseService.sseSend(id, msg);
    }
}
