package com.woozuda.backend.question.service;

import com.woozuda.backend.question.entity.Question;
import com.woozuda.backend.question.repository.QuestionRepository;
import com.woozuda.backend.question.service.dto.request.AiQuestionRequestDto;
import com.woozuda.backend.question.service.dto.response.AiQuestionResponseDto;
import com.woozuda.backend.question.service.util.AiInputGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AiQuestionCreationService {

    private final QuestionRepository questionRepository;
    private final AiQuestionCreationApiClient apiClient;

    @Value("${ncp.clova-studio.question-creation.api-key}")
    private String apiKey;

    @Value("${ncp.clova-studio.question-creation.apigw-key}")
    private String apigwKey;

    // 매일 자정 12시 00분 1초에 새로운 질문 생성
    @Scheduled(cron = "1 0 0 * * *")
    public void makeTodayAiQuestion() {
        AiQuestionRequestDto requestDto = AiQuestionRequestDto.of(AiInputGenerator.execute());
        log.info("[AI Question Creator] input={}", requestDto.getText());

        AiQuestionResponseDto responseDto = apiClient.makeAiQuestion(
                apiKey,
                apigwKey,
                requestDto
        );

        if (hasError(responseDto)) {
            throw new IllegalArgumentException("API 요청에 실패했습니다");
        }

        String output = responseDto.getResult().getText();
        log.info("[AI Question Creator] output={}", output);

        questionRepository.save(Question.of(output));
    }

    private boolean hasError(AiQuestionResponseDto response) {
        String code = response.getStatus().getCode();
        return !code.equals("20000") && !code.equals("20400");
    }

}
