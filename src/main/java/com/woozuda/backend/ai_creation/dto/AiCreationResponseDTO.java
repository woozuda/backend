package com.woozuda.backend.ai_creation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AiCreationResponseDTO {
    private LocalDate start_date;
    private LocalDate end_date;
    private String imageUrl;
    private String text;
}
