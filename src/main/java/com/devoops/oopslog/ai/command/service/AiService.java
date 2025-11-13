package com.devoops.oopslog.ai.command.service;

import com.devoops.oopslog.ai.query.mapper.AiReadMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@EnableAsync
@Service
public class AiService {

    @Value("${openai.api.key}")
    private String openAiApiKey;

    private static final String OPENAI_URL = "https://openrouter.ai/api/v1/chat/completions";
    private static final String MODEL = "gpt-4o-mini";

    private final RestTemplate restTemplate;
    private final AiReadMapper aiReadMapper;

    public AiService(RestTemplate restTemplate, AiReadMapper aiReadMapper) {
        this.restTemplate = restTemplate;
        this.aiReadMapper = aiReadMapper;
    }

    public String getAiFeedback(String content) {
        RestTemplate rt = restTemplate;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(openAiApiKey);

        String systemPrompt = """
        너는 따뜻하고 진심 어린 친구야.
        사용자가 힘들거나 외로운 이야기를 하면, 논리적으로 분석하지 말고
        먼저 감정에 공감하고, 마음을 어루만지는 따뜻한 세 문장으로 30자 이내로 대답해줘.

        항상 사람처럼 자연스럽게 말하고 적절히 감정을 전달해줘.
        말투는 부드럽고, 너무 형식적이거나 딱딱하지 않게 해.
        기본적으로 감정을 전달하는 말만 하면돼. 물어본 이야기와 연관되지 않은 이야기는 할 필요없어.
        말을 의문문으로 하지마.
        예를 들어:
        - "정말 고생 많았겠다…"
        - "그랬구나, 마음이 아팠겠다"
        - "정말 잘했어! 자랑스러워"
        """;

        Map<String, Object> body = Map.of(
                "model", MODEL,
                "messages", List.of(
                        Map.of("role", "system", "content", systemPrompt),
                        Map.of("role", "user", "content", content)
                ),
                "temperature", 0.9
        );

        HttpEntity<Map<String, Object>> req = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> res = rt.postForEntity(OPENAI_URL, req, Map.class);
            Map<String, Object> choice = (Map<String, Object>) ((List<?>) res.getBody().get("choices")).get(0);
            Map<String, String> message = (Map<String, String>) choice.get("message");
            return message.get("content").trim();
        } catch (Exception e) {
            return "AI 피드백 오류: " + e.getMessage();
        }
    }

    public String getRelatedEmoTags(String content) {
        RestTemplate rt = restTemplate;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(openAiApiKey);

        // DB에서 감정 태그 목록 조회
        List<String> emoTagList = aiReadMapper.emoTag();
        String emoTags = String.join(", ", emoTagList);

        // 프롬프트 작성
        String prompt = """
        너는 감정 분석 전문가야.
        아래는 데이터베이스에서 불러온 감정 태그 목록이야.
        반드시 이 목록 안의 단어 중에서만 선택해야 해.

        [태그 목록]
        %s

        ---

        [사용자 글 내용]
        "%s"

        ---

        이 글의 감정과 의미상 관련이 깊은 태그를 3개 이내로 찾아줘.
        추가 설명 없이 반드시 JSON 형식으로만 출력해.
        예시:
        {
          "tags": ["행복", "감사"]
        }
        """.formatted(emoTags, content);

        Map<String, Object> body = Map.of(
                "model", MODEL,
                "messages", List.of(Map.of("role", "user", "content", prompt)),
                "temperature", 0.7
        );

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> response = rt.postForEntity(OPENAI_URL, request, Map.class);

            Map<String, Object> choice = (Map<String, Object>) ((List<?>) response.getBody().get("choices")).get(0);
            Map<String, String> message = (Map<String, String>) choice.get("message");

            return message.get("content").trim();
        } catch (Exception e) {
            return "{ \"error\": \"" + e.getMessage() + "\" }";
        }
    }

    /**
     * (비동기 예시) AI 피드백 비동기 호출
     */
    @Async
    public CompletableFuture<String> getAiFeedbackAsync(String content) {
        return CompletableFuture.completedFuture(getAiFeedback(content));
    }
}
