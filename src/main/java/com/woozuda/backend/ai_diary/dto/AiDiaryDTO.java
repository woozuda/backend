package com.woozuda.backend.ai_diary.dto;

import com.woozuda.backend.ai_diary.entity.AiDiary;
import lombok.*;

import java.time.LocalDate;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class AiDiaryDTO {
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



