package com.woozuda.backend.ai_diary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 응답 결과 DTO GET
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AiDiaryResponseDTO {
    private LocalDate start_date; // 월요일
    private LocalDate end_date; // 일요일
    private String place; // 장소
    private String activity; // 활동
    private String emotion; // 감정
    private String weather; // 날씨에 따른 내 모습
    private float weekdayAt; // 평일 비중
    private float weekendAt; // 주말 비중
    private float positive; // 긍정 감정
    private float denial; // 부정 감정
    private String suggestion; // 제안
}
