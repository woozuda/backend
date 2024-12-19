package com.woozuda.backend.ai_diary.dto;

import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AiDiaryCountResponseDTO {
    private AiDiaryResponseDTO aiDiaryResponseDTO;
    private long diaryCount;
}
