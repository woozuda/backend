package com.woozuda.backend.ai.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatGptService {
    /**
     * chat GPT 연동하는 서비스 로직 입니다.
     */

    private final WebClient webClient;

    @Value("${openai.api.url}")
    private String apiUrl;

    private static final int MAX_CONTEXT_TOKENS = 4096; // max 토큰 값 설정

    public Mono<String> analyzeDiaryUsingGPT(String systemMessage, String userMessage) {
        int systemMessageTokens = estimateTokenCount(systemMessage);
        int remainingTokens = MAX_CONTEXT_TOKENS - systemMessageTokens;

        int userMessageTokens = estimateTokenCount(userMessage);
        int maxTokens = remainingTokens - userMessageTokens;
        maxTokens = Math.max(maxTokens, 50);

        Map<String, Object> requestBody = Map.of(
                "model", "gpt-3.5-turbo",
                "messages", new Object[]{
                        Map.of("role", "system", "content", systemMessage),
                        Map.of("role", "user", "content", userMessage)
                },
                "max_tokens", maxTokens
        );

        return webClient.post()
                .uri(apiUrl)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class); // 비동기 응답 그대로 반환
    }

    private int estimateTokenCount(String text) {
        // 간단한 토큰 계산: 한글 기준으로 1.5~2배로 계산
        return text.split("\\s+").length * 2; // 한 단어에 대해 2토큰으로 계산
    }

}



