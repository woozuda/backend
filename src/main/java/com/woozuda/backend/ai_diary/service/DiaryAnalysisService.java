package com.woozuda.backend.ai_diary.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woozuda.backend.ai_diary.dto.AiDiaryDTO;
import com.woozuda.backend.ai_diary.dto.DiaryDTO;
import com.woozuda.backend.ai_diary.entity.AiDiary;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class DiaryAnalysisService {
    private final ChatGptService  chatGptService;
    private final ObjectMapper objectMapper; // JSON 파싱을 위한 ObjectMapper 주입
    private final AiDiaryService aiDiaryService;  // AiDiaryService 주입
    /**
     * 프롬포트 서비스 로직
     * AI가 원하는 질문을 얻기위한게 프롬포트 ->  systemMessage
     * User 주는 정보 -> 이 부분 변경이 필요한 작업
     * 원하는 결과값 DTO 형식으로 반환
     *
     * @param diaryDto
     * @return
     */

    public AiDiaryDTO analyzeDiary(DiaryDTO diaryDto) {
        // 프롬프트 정의
        String systemMessage = """
                당신은 분석 도우미입니다. 사용자의 일기를 분석하고 다음과 같은 정보를 제공하세요:
                1. 주요 장소 ,활동 , 감정 (목록 형식)
                2. 날씨에 따른 기분 분석 (날씨와 감정 유형)
                3. 평일/주말 비율
                4. 개선 사항이나 추천 행동
                5. 위의 내용을 포함하여 객체 타입으로 변환해주세요. 예:
                   place: "장소1, 장소2"
                   activity: "활동1, 활동2"
                   emotion: "주요감정1" , "주요감정2"
                   weather: "비가 올때는 눈물이 난다. 날씨가 맑을때 기분이 좋다."
                   weekdayAt: 50.0
                   weekendAt: 50.0
                   positive: 80.0
                   denial: 20.0
                   suggestion: "일정 속에서 조금 더 휴식을 취하고, 자신만의 시간을 갖는 것이 중요해 보입니다."
                
                   각 항목을 객체 타입으로 반환해주세요.
                """;
        //이쪽 부분 로직을 변경해야하는 부분 -> 실제 사용자 일기를 가져오는지 -> 일단 LIST 형식으로 넣어보기
        String userMessage = String.format("""
                        일기 분석 요청:
                        제목: %s
                        날짜: %s
                        감정: %s
                        날씨: %s
                        본문: %s
                        """,
                diaryDto.getTitle(),
                diaryDto.getDate(),
                diaryDto.getEmotion(),
                diaryDto.getWeather(),
                diaryDto.getContent()
        );

        // ChatGPT API 호출
        String response = chatGptService.analyzeDiaryUsingGPT(systemMessage, userMessage);

        // 이 부분은 실제로 ai가 값을 추출 합니다.
        //log.info("ai가 추출한 값" + response);

        // GPT 응답을 AiDiaryDTO로 매핑
        AiDiaryDTO aiDiaryDTO = mapResponseToAiDiaryDTO(response);

        /**
         * 분석된 결과를 DB에 저장 -> DiaryAnalysisService -> AiDiaryService -> AiDiaryRpository -> DB
         */
        AiDiary savedAiDiary = aiDiaryService.saveAiDiary(aiDiaryDTO);  // DB에 저장하고 엔티티 반환

        /**
         * 분석된 결과를 화면에 바로 출력 이 부분 id null 임으로 db에 저장된 값이 아님
         */
        return new AiDiaryDTO(
                savedAiDiary.getPlace(),
                savedAiDiary.getActivity(),
                savedAiDiary.getEmotion(),
                savedAiDiary.getWeather(),
                savedAiDiary.getWeekdayAt(),
                savedAiDiary.getWeekendAt(),
                savedAiDiary.getPositive(),
                savedAiDiary.getDenial(),
                savedAiDiary.getSuggestion()
        );
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