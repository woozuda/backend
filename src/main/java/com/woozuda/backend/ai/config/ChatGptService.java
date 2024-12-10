package com.woozuda.backend.ai.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatGptService {
    /**
     * chat GPT 연동하는 서비스 로직 입니다.
     */
    private final RestTemplate restTemplate;

//    @Value("${spring.jwt.hashcode}")
//    private String jwtToken;

    @Value("${openai.api.key}")
    private String apiKey;

    private static final int MAX_CONTEXT_TOKENS = 4096; // max 토큰 값 설정

    public String analyzeDiaryUsingGPT(String systemMessage, String userMessage) {
        int systemMessageTokens = estimateTokenCount(systemMessage);
        int remainingTokens = MAX_CONTEXT_TOKENS - systemMessageTokens;

        // userMessage의 토큰 수가 남은 토큰 수를 초과하지 않도록 계산
        int userMessageTokens = estimateTokenCount(userMessage);
        int maxTokens = remainingTokens - userMessageTokens;

        // maxTokens가 0보다 작지 않도록 확인
        maxTokens = Math.max(maxTokens, 50); // 최소 50 토큰은 남겨두기

        String apiUrl = "https://api.openai.com/v1/chat/completions"; // 실제 ChatGPT API URL

        // 요청 데이터 구성
        Map<String, Object> requestBody = Map.of(
                "model", "gpt-3.5-turbo", // GPT 3.5 모델 사용
                "messages", new Object[]{
                        Map.of("role", "system", "content", systemMessage),
                        Map.of("role", "user", "content", userMessage)
                },
                "max_tokens", maxTokens // 응답에 사용할 최대 토큰 수 설정
        );

        // HttpHeader에 추가
        HttpHeaders headers = new HttpHeaders();
        //headers.set("Authorization", "Bearer " + jwtToken);
        headers.set("x-api-key", apiKey); // x-api-key 헤더에 API 키 설정
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);

        // HttpEntity로 요청과 헤더를 묶어서 전송
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        try {
            // HTTP 요청 보내기
            ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class);

            // API 응답이 성공적일 경우 반환된 데이터 리턴
            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                throw new RuntimeException("AI 호출 실패: " + response.getStatusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("AI 호출 중 오류 발생: " + e.getMessage());

        }
    }
    private int estimateTokenCount(String text) {
        // 간단한 토큰 계산: 한글 기준으로 1.5~2배로 계산
        return text.split("\\s+").length * 2; // 한 단어에 대해 2토큰으로 계산
    }
}



