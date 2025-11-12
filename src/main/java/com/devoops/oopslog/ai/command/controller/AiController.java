package com.devoops.oopslog.ai.command.controller;

import com.devoops.oopslog.ai.command.service.AiService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/ai")
public class AiController {

    @Autowired
    private AiService aiService;

    /**
     * 하나의 POST 요청으로
     * 감정 피드백
     * 관련 감정 태그 추천
     * 을 동시에 반환
     *
     * 요청 예시:
     * POST /ai/analyze
     * {
     *   "content": "오늘 너무 힘들었지만 그래도 해냈어."
     * }
     *
     * 응답 예시:
     * {
     *   "feedback": "정말 잘했어! 자랑스러워 😊",
     *   "relatedTags": ["성취", "감사"]
     * }
     */
    @PostMapping("/analyze")
    public ResponseEntity<Map<String, Object>> analyzeEmotion(@RequestBody Map<String, String> request) {
        String content = request.get("content");
        if (content == null || content.isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "content 필드는 필수입니다."));
        }

        //  AI 감정 피드백 생성
        String feedback = aiService.getAiFeedback(content);

        // 감정 태그 추천 (JSON 문자열)
        String relatedTagsJson = aiService.getRelatedEmoTags(content);

        // 🔹 JSON 문자열을 실제 Map으로 변환
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> relatedTagsMap;
        try {
            relatedTagsMap = mapper.readValue(relatedTagsJson, Map.class);
        } catch (Exception e) {
            relatedTagsMap = Map.of("raw", relatedTagsJson);
        }

        // 🔹 최종 응답 JSON 구성
        Map<String, Object> response = new HashMap<>();
        response.put("feedback", feedback);
        response.put("relatedTags", relatedTagsMap.get("tags"));

        return ResponseEntity.ok(response);
    }
}
