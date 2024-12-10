package com.woozuda.backend.ai_recall.dto;

import com.woozuda.backend.ai_recall.entity.AirecallType;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Airecall_Pmi_DTO {
    private AirecallType airecallType;
    private LocalDate start_date;
    private LocalDate end_date;
    private String positive;
    private String minus;
    private String interesting;
    private String conclusion_action;
    private String username;
}
