package com.devoops.oopslog.common;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class SseRepository {
    private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

    public SseEmitter save(Long id, SseEmitter emitter) {
        emitters.put(id, emitter);
        return emitter;
    }

    public SseEmitter get(Long id) {
        return emitters.get(id);
    }

    public void delete(Long id) {
        emitters.remove(id);
    }
}
