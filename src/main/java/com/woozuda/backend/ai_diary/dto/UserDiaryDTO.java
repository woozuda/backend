package com.woozuda.backend.ai_diary.dto;

import com.woozuda.backend.ai_diary.entity.DiaryEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@Data
@ToString
/**
 * 여기 부분을 일기 부분으로 이제 바꿀예장 -> userdiary를 받아와야해서  -> 임시 DTO
 */
public class UserDiaryDTO {
    private String startDate;
    private String endDate;
    private List<DiaryEntity> diaries; // 일기 목록

}
