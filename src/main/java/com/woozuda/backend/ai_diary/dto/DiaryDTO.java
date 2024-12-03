package com.woozuda.backend.ai_diary.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
/**
 * 여기 부분을 일기 부분으로 이제 바꿀예장 -> userdiary를 받아와야해서  -> 임시 DTO
 */
public class DiaryDTO {
    private String title;
    private String date;
    private String emotion;
    private String weather;
    private String content;
}
