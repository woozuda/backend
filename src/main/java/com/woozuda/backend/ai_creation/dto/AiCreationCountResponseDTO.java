package com.woozuda.backend.ai_creation.dto;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AiCreationCountResponseDTO {
    private AiCreationResponseDTO aiCreationResponseDTO;
    private long diaryCount;
}
