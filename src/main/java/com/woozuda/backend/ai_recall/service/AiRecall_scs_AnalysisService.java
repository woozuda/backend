package com.woozuda.backend.ai_recall.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woozuda.backend.ai.config.ChatGptService;
import com.woozuda.backend.ai_recall.dto.Airecall_4fs_DTO;
import com.woozuda.backend.ai_recall.dto.Airecll_Scs_DTO;
import com.woozuda.backend.ai_recall.entity.AirecallType;
import com.woozuda.backend.forai.dto.RetroNoteEntryResponseDto;
import com.woozuda.backend.forai.service.CustomeNoteRepoForAiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class AiRecall_scs_AnalysisService {
    private final ChatGptService chatGptService;
    private final ObjectMapper objectMapper;
    private final AiRecallService aiRecallService;
    private final CustomeNoteRepoForAiService customeNoteRepoForAiService;

    public void analyzeAirecall(List<RetroNoteEntryResponseDto> recallList , String username) {
        // 회고 분석 요청 메시지 작성
        StringBuilder userMessage = new StringBuilder();


        // 회고 내용 추가
        for (RetroNoteEntryResponseDto recall : recallList) {
            userMessage.append("title: ").append(recall.getTitle()).append("\n");
            userMessage.append("date: ").append(recall.getDate()).append("\n");
            userMessage.append("framework").append(recall.getFramework()).append("\n");
            userMessage.append("content").append(recall.getContent()).append("\n");
        }

        // 프롬프트 정의
        String systemMessage = """
               당신은 분석 도우미입니다. 사용자의 회고 데이터를 분석하고 다음과 같은 정보를 제공하세요:
                1. start_date 와 end_date 는 사용자가 입력한 값 그대로 String 타입으로 출력하세요.
                   - 예를 들어 "2024-10-12"로 입력되었다면, 정확히 이 값을 출력하세요. 절때 Null 반환 금지
                2. type 은 분석하지 말고 사용자가 입력한 값 그대로 String 타입으로 출력하세요.
                    그리고 만약 "FOUR_F_S" 타입으로 입력되었다면 4FS로 정확히 출력해주세요. 절때 Null 반환 금지
                    start_date: 2024-12-01
                    end_date: 2024-12-31
                    type : SCS
                    start_summary :
                    start_strength :
                    start_suggestion :
                    continue_summary: 
                    continue_strength:
                    continue_suggestion:
                    stop_summary:
                    stop_strength:
                    stop_suggestion:
                    start_improvement_plan:
                    continue_improvement_plan:
                    stop_improvement_plan:
               """;
        log.info("사용자 메시지 내용: {}", userMessage.toString());
        // ChatGPT API 호출
        String response = chatGptService.analyzeDiaryUsingGPT(systemMessage, userMessage.toString());

        log.info("AI 응답 내용: {}", response);

        // 응답 매핑
        Airecll_Scs_DTO airecall_scs_dto = mapResponseToAirecall(response , username);

        // DB에 저장
        aiRecallService.saveAirecall_scs(airecall_scs_dto);
    }

    private Airecll_Scs_DTO mapResponseToAirecall(String response , String username) {
        try {
            JsonNode root = objectMapper.readTree(response);
            JsonNode contentNode = root
                    .path("choices")
                    .get(0)
                    .path("message")
                    .path("content");

            String content = contentNode.asText();
            /**
             * 날짜 변경
             */
            String startDateStr = extractValue(content, "start_date").replaceAll("\"", "").replace(",", "").trim();
            String endDateStr = extractValue(content, "end_date").replaceAll("\"", "").replace(",", "").trim();

            LocalDate startDate = convertStringToDate(startDateStr);
            LocalDate endDate = convertStringToDate(endDateStr);
            /**
             * type 변경
             */
            String airecallType = extractValue(content, "type");

            String start_summary = extractValue(content, "start_summary");
            String start_strength = extractValue(content, "start_strength");
            String start_suggestion = extractValue(content, "start_suggestion");
            String continue_summary = extractValue(content, "continue_summary");
            String continue_strength = extractValue(content, "continue_strength");
            String continue_suggestion = extractValue(content, "continue_suggestion");
            String stop_summary = extractValue(content, "stop_summary");
            String stop_strength = extractValue(content, "stop_strength");
            String stop_suggestion = extractValue(content, "stop_suggestion");
            String start_improvement_plan = extractValue(content, "start_improvement_plan");
            String continue_improvement_plan = extractValue(content, "continue_improvement_plan");
            String stop_improvement_plan = extractValue(content, "stop_improvement_plan");


            return new Airecll_Scs_DTO(
                    airecallType,
                    startDate,
                    endDate,
                    start_summary,
                    start_strength,
                    start_suggestion,
                    continue_summary,
                    continue_strength,
                    continue_suggestion,
                    stop_summary,
                    stop_strength,
                    stop_suggestion,
                    start_improvement_plan,
                    continue_improvement_plan,
                    stop_improvement_plan,
                    username
            );

        } catch (Exception e) {
            log.error("응답 매핑 중 오류 발생: {}", e.getMessage(), e);
            throw new RuntimeException("응답 매핑 중 오류 발생: " + e.getMessage());
        }
    }

    private LocalDate convertStringToDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            return LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            log.error("잘못된 날짜 형식: '{}'. 기본값을 사용합니다.", date);
            return LocalDate.now(); // 기본값 설정
        }
    }

    private String extractValue(String content, String key) {
        if (content == null || content.isEmpty()) {
            log.warn("내용이 비어 있음: {}", key);
            return "분석불가";
        }

        // 키에 따른 정규식 패턴
        String pattern = key + "\\s*:\\s*(.*?)(?=\\n|$)";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(content);

        if (matcher.find()) {
            String extractedValue = matcher.group(1).trim();
            return extractedValue.isEmpty() ? "값 없음" : extractedValue;
        }

        log.warn("값 추출 실패: {}", key);
        return "분석불가"; // 기본값 설정
    }
}