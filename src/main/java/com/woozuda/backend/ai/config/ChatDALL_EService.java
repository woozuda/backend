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
public class ChatDALL_EService {

    private final RestTemplate restTemplate;
    @Value("${openai.api.key}")
    private String apiKey;

    public String generateImageUsingDallE(String prompt) {
        String apiUrl = "https://api.openai.com/v1/images/generations";

        // 요청 데이터 구성
        Map<String, Object> requestBody = Map.of(
                "prompt", prompt, // 생성할 이미지에 대한 설명
                "n", 1,           // 생성할 이미지 개수
                "size", "512x512" // 이미지 크기
        );

        // HttpHeader에 추가
        org.springframework.http.HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

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
}
