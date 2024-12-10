package com.woozuda.backend.ai.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;

/**
 * ChatGPT API 통합을 위한 설정 클래스입니다.
 * RestTemplate 및 HTTP 헤더 관련 설정
 */
@Configuration
@Slf4j
public class ChatGPTConfig {
    /**
     * 사용자가 로그인 한 상태에서 함으로 필요 없음
     */
    // OpenAI API 키를 application.properties 또는 application.yml 파일에서 가져옵니다.
//    @Value("${spring.jwt.hashcode}")
//    private String jwtToken;

    @Value("${openai.api.key}")
    private String apiKey;




    /**
     * RestTemplate 빈 설정.
     * ChatGPT API와 같은 외부 서비스에 HTTP 요청을 보낼 때 사용됩니다.
     *
     * @return RestTemplate 인스턴스
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * ChatGPT API 요청에 사용되는 HTTP 헤더를 설정
     *
     * @return HttpHeaders 인스턴스
     */
    @Bean
    public HttpHeaders httpHeaders() {
        HttpHeaders headers = new HttpHeaders();

        // JWT 토큰을 Authorization 헤더에 추가
        //headers.set("Authorization", "Bearer " + jwtToken);

        // API 키를 x-api-key 헤더에 추가
        headers.set("x-api-key", apiKey);

        // 요청 본문을 JSON 형식으로 설정
        headers.setContentType(MediaType.APPLICATION_JSON);

        return headers;
    }
}