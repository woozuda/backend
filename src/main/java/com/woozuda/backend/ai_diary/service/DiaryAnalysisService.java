package com.woozuda.backend.ai_diary.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woozuda.backend.ai.config.ChatGptService;
import com.woozuda.backend.ai_diary.dto.AiDiaryDTO;
import com.woozuda.backend.ai_diary.dto.UserDiaryDTO;
import com.woozuda.backend.ai_diary.entity.AiDiary;
import com.woozuda.backend.ai_diary.entity.DiaryEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.time.format.DateTimeParseException;

@Slf4j
@Service
@RequiredArgsConstructor
public class DiaryAnalysisService {
    private final ChatGptService chatGptService;
    private final ObjectMapper objectMapper; // JSON 파싱을 위한 ObjectMapper 주입
    private final AiDiaryService aiDiaryService;  // AiDiaryService 주입
    /**
     * 프롬포트 서비스 로직
     * AI가 원하는 질문을 얻기위한게 프롬포트 ->  systemMessage
     * User 주는 정보 -> 이 부분 변경이 필요한 작업
     * 원하는 결과값 DTO 형식으로 반환
     *
     * @param diaryDto
     */

    public void analyzeDiary(UserDiaryDTO diaryDto) {
        // StringBuilder로 메시지 작성
        StringBuilder userMessage = new StringBuilder();

        // 기간
        userMessage.append("start_date: ").append(diaryDto.getStartDate()).append("\n");
        userMessage.append("end_date: ").append(diaryDto.getEndDate()).append("\n");

        // 일기 내용 반복문
        for (DiaryEntity diary : diaryDto.getDiaries()) {
            userMessage.append("제목: ").append(diary.getTitle()).append("\n");
            userMessage.append("날짜: ").append(diary.getDate()).append("\n");
            userMessage.append("감정: ").append(diary.getEmotion()).append("\n");
            userMessage.append("날씨: ").append(diary.getWeather()).append("\n");
            userMessage.append("본문: ").append(diary.getContent()).append("\n\n");
        }

        // 프롬프트 정의
        String systemMessage = """
                // 프롬포트 추가 
                당신은 분석 도우미입니다. 사용자의 일기를 분석하고 다음과 같은 정보를 제공하세요:
                1. start_date와 end_date는 사용자가 입력한 값 그대로 String 타입으로 출력하세요.
                   - 예를 들어 "2024-10-12"로 입력되었다면, 정확히 이 값을 출력하세요.
                2. 주요 장소(place), 주요 활동(activity), 주요 감정(emotion)을 반드시 분석해 값을 반환해서 목록 형식으로 제공
                   - 분석이 불가능한 경우 비슷한 데이터라도 출력해주세요. 절때 Null 반환 금지
                3. 사용자가 제공한 날씨 데이터를 감정을 기반으로 분석 결과를 작성하세요.
                  - 분석이 불가능한 경우 비슷한 데이터라도 출력해주세요. 절때 Null 반환 금지
                4. 평일/주말 비율(weekdayAt, weekendAt)을 분석이 가능하면 각각 반드시 백분율로 출력해주세요.
                    - `weekdayAt`과 `weekendAt`의 합은 꼭 100%가 되어야 합니다. 절때 Null 과 0.0을 출력하지 마세요 
                5. 긍정적 감정(positive)과 부정적 감정(denial)이 분석이 가능하면 긍정적 감정과 부정적 감정의 비율을 각각 백분율로 출력홰주세요.
                    - `positive`와 `denial`의 합은 꼭 100%가 되어야 합니다. 절때 Null 과 0.0을 출력하지 마세요 
                6. 개선 사항이나 추천 행동(suggestion)을 반드시 작성하세요.
                 - 분석이 불가능한 경우 비슷한 데이터라도 출력해주세요. 절때 Null 반환 금지
                7. 위의 내용을 포함하여 각 항목을 객체 타입으로 한번만 반환해주세요. 예:
                    start_date: 2024-12-01
                    end_date: 2024-12-31
                      place: "장소1, 장소2"
                      activity: "활동1, 활동2"
                      emotion: "주요감정1" , "주요감정2"
                      weather: "비가 올때는 눈물이 난다. 날씨가 맑을때 기분이 좋다."
                      weekdayAt: 50.0
                      weekendAt: 50.0
                      positive: 80.0
                      denial: 20.0
                      suggestion: "일정 속에서 조금 더 휴식을 취하고, 자신만의 시간을 갖는 것이 중요해 보입니다."      
                """;;


        // ChatGPT API 호출
        String response = chatGptService.analyzeDiaryUsingGPT(systemMessage, userMessage.toString());

        // 이 부분은 실제로 ai가 값을 추출 합니다.
        //log.info("ai가 추출한 값" + response);

        // GPT 응답을 AiDiaryDTO로 매핑
        AiDiaryDTO aiDiaryDTO = mapResponseToAiDiaryDTO(response);

        /**
         * 분석된 결과를 DB에 저장 -> DiaryAnalysisService -> AiDiaryService -> AiDiaryRpository -> DB
         */
        AiDiary savedAiDiary = aiDiaryService.saveAiDiary(aiDiaryDTO);  // DB에 저장하고 엔티티 반환

    }

    /**
     * 응답 받은 형식을 DTO로 변환 -> 그 다음 DB타입으로 저장하는 형식.
     * @param response
     * @return
     */
    private AiDiaryDTO mapResponseToAiDiaryDTO(String response) {
        try {
            JsonNode root = objectMapper.readTree(response);
            JsonNode contentNode = root
                    .path("choices")
                    .get(0)
                    .path("message")
                    .path("content");

            String content = contentNode.asText();

            // 항목 추출
            String startDateStr = extractValue(content, "start_date");
            String endDateStr = extractValue(content, "end_date");

            // 날짜 형식 변환: String -> LocalDate
            LocalDate startDate = convertStringToDate(startDateStr);
            LocalDate endDate = convertStringToDate(endDateStr);

            // 항목 추출
            String place = extractValue(content, "place");
            String activity = extractValue(content, "activity");
            String emotion = extractValue(content, "emotion");
            String weather = extractValue(content, "weather");

            /**
             *  감정 비율 처리
             */

            String positiveStr = extractValue(content, "positive");
            String denialStr = extractValue(content, "denial");
            /**
             * String -> float 형식으로 바꿈
             */
            float positive = convertStringToFloat(positiveStr);
            float denial = convertStringToFloat(denialStr);

            /**
             *  평일 ,주말 비율 형식으로 변환
             */
            String weekdayRatioStr = extractValue(content, "weekdayAt");
            String weekendRatioStr = extractValue(content, "weekendAt");
            /**
             * String -> float 형식으로 바꿈
             */
            float weekday = convertStringToFloat(weekdayRatioStr);
            float weekend = convertStringToFloat(weekendRatioStr);

            String suggestion = extractValue(content, "suggestion");

            return new AiDiaryDTO(
                    startDate,      // 예시: LocalDate.parse("2024-11-18")
                    endDate,
                    place,
                    activity,
                    emotion,
                    weather,
                    weekday,
                    weekend,
                    positive,
                    denial,
                    suggestion

            );
        } catch (Exception e) {
            log.error("응답 매핑 중 오류 발생: {}", e.getMessage(), e);
            throw new RuntimeException("응답 매핑 중 오류 발생: " + e.getMessage());
        }
    }


    private LocalDate convertStringToDate(String dateStr) {
        try {
            return LocalDate.parse(dateStr); // 날짜 형식에 맞게 변환
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format: " + dateStr);
        }
    }
    /**
     * 문자열(value)에서 숫자 데이터를 추출하고 이를 float 타입으로 변환
     * ai 가 추출해주는 값은 100.0은 String -> float 형식으로 변환
     * @param value
     * @return
     */
    private float convertStringToFloat(String value) {
        if (value == null || value.isEmpty()) {
            return 0.0f;
        }
        // 숫자와 소수점(.)만 남기기
        String numberStr = value.replaceAll("[^0-9.]", "");
        try {
            // Float.parseFloat 사용 시, 100.0 과 같은 형태가 정상적으로 처리됨
            return Float.parseFloat(numberStr);
        } catch (NumberFormatException e) {
            log.error("비율 파싱 오류: {}", e.getMessage(), e);
            return 0.0f;
        }

    }

    /**
     * 실제 ai가 분석한 답변을 키를 찾아서 그 내용을 dto에 넣어주는 형식
     * ex) "place": "\"집\"",
     * : 기준으로 place -> key 값  \"집\"" -> value 값
     * \"집\"" -> dto에 넣어줌.
     * @param content
     * @param key
     * @return
     */
    private String extractValue(String content, String key) {
        if (content == null || content.isEmpty()) {
            //log.warn("내용이 비어 있음: {}", key);
            return "분석불가";
        }

        String pattern = key + ":(.*?)(?=\n|$)";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(content);

        if (matcher.find()) {
            String extractedValue = matcher.group(1).trim();
            return extractedValue.isEmpty() ? "값 없음" : extractedValue;
        }

        //log.warn("값을 추출할 수 없음: {}", key);
        return "분석불가";
    }
}