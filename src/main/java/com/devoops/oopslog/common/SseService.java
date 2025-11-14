package com.devoops.oopslog.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;

@Service
@Slf4j
public class SseService {
    private final SseRepository sseRepository;

    public SseService(SseRepository sseRepository) {
        this.sseRepository = sseRepository;
    }

    public SseEmitter sseSubscribe(Long id) {
        SseEmitter emitter = new SseEmitter(1800000L);

        emitter.onCompletion(() -> {
            sseRepository.delete(id);
            emitter.complete();
            log.info("sse 연결 종료");
        });
        emitter.onError((e) -> {
            sseRepository.delete(id);
            emitter.complete();
            log.info("sse 연결 에러");
        });
        emitter.onTimeout(() -> {
            sseRepository.delete(id);
            emitter.complete();
            log.info("sse 연결 시간 만료");
        });
        sseRepository.save(id, emitter);
        return emitter;
    }

    public void sseUnsubscribe(Long id) {
        log.info("{}번 회원 SSE 연결 종료", id);
        sseRepository.delete(id);
    }

    public void sseSend(Long id, String message) {
        // SseEmitter 생성
        SseEmitter emitter = sseRepository.get(id);

        // 만약 구독되어있는 id가 없으면 return
        if (emitter == null) return;

        try {
            emitter.send(SseEmitter.event().data(
                    Map.of("message", "\""+message+"\" 게시물에 댓글이 달렸습니다.")
            ));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
