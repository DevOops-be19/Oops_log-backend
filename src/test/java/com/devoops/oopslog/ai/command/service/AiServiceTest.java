package com.devoops.oopslog.ai.command.service;

import com.devoops.oopslog.ai.query.mapper.AiReadMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.*;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AiServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private AiReadMapper aiReadMapper;

    @InjectMocks
    private AiService aiService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);

        aiService = Mockito.spy(new AiService(restTemplate, aiReadMapper));
        // private @Value 필드 강제 주입
        ReflectionTestUtils.setField(aiService, "openAiApiKey", "test-api-key");
    }

    // ====================================================================
    @Test
    @DisplayName("AI 피드백 성공 응답 파싱 확인")
    void testGetAiFeedbackSuccess() {

        // Mock 응답 데이터 구성
        Map<String, Object> message = Map.of("content", "정말 고생 많았겠다…");
        Map<String, Object> choice = Map.of("message", message);
        Map<String, Object> mockBody = Map.of("choices", List.of(choice));

        ResponseEntity<Map> mockResponse =
                new ResponseEntity<>(mockBody, HttpStatus.OK);

        // RestTemplate Mock 동작 설정
        when(restTemplate.postForEntity(anyString(), any(), eq(Map.class)))
                .thenReturn(mockResponse);

        // 실행
        String result = aiService.getAiFeedback("오늘 힘들었어");

        // 검증
        assertEquals("정말 고생 많았겠다…", result);
    }

    // ====================================================================
    @Test
    @DisplayName("AI 피드백 실패 시 예외 처리 메시지 반환")
    void testGetAiFeedbackException() {

        when(restTemplate.postForEntity(anyString(), any(), eq(Map.class)))
                .thenThrow(new RuntimeException("API 오류"));

        String result = aiService.getAiFeedback("테스트");

        assertTrue(result.contains("AI 피드백 오류"));
    }

    // ====================================================================
    @Test
    @DisplayName("AI 감정 태그 추천 - 정상 응답 처리")
    void testGetRelatedEmoTagsSuccess() {
        // DB 태그 Mock
        when(aiReadMapper.emoTag()).thenReturn(List.of("행복", "감사", "위로"));

        // AI 응답 Mock
        Map<String, Object> message = Map.of(
                "content", """
                {
                    "tags": ["행복", "감사"]
                }
                """
        );
        Map<String, Object> choice = Map.of("message", message);
        Map<String, Object> mockBody = Map.of("choices", List.of(choice));
        ResponseEntity<Map> mockResponse =
                new ResponseEntity<>(mockBody, HttpStatus.OK);

        when(restTemplate.postForEntity(anyString(), any(), eq(Map.class)))
                .thenReturn(mockResponse);

        // 실행
        String result = aiService.getRelatedEmoTags("오늘 매우 기뻤어!");

        // 검증
        assertTrue(result.contains("행복"));
        assertTrue(result.contains("감사"));
    }

    // ====================================================================
    @Test
    @DisplayName("AI 감정 태그 추천 실패 시 error JSON 반환")
    void testGetRelatedEmoTagsException() {
        when(aiReadMapper.emoTag()).thenReturn(List.of("슬픔", "기쁨"));

        when(restTemplate.postForEntity(anyString(), any(), eq(Map.class)))
                .thenThrow(new RuntimeException("OpenAI 오류"));

        String result = aiService.getRelatedEmoTags("테스트");

        assertTrue(result.contains("error"));
    }

    // ====================================================================
    @Test
    @DisplayName("비동기 AI 피드백 테스트")
    void testAiFeedbackAsync() throws Exception {
        // getAiFeedback() Mock
        Mockito.doReturn("좋았어!")
                .when(aiService)
                .getAiFeedback("테스트");

        CompletableFuture<String> future =
                aiService.getAiFeedbackAsync("테스트");

        assertEquals("좋았어!", future.get());
    }
}
