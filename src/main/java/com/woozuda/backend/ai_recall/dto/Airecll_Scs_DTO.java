package com.woozuda.backend.ai_recall.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Airecll_Scs_DTO {
    private String airecallType;

    private LocalDate start_date;

    private LocalDate end_date;

    private String start_summary;

    private String start_strength;

    private String start_suggestion;

    private String continue_summary;

    private String continue_strength;

    private String continue_suggestion;

    private String stop_summary;

    private String stop_strength;

    private String stop_suggestion;

    private String start_improvement_plan;

    private String continue_improvement_plan;

    private String stop_improvement_plan;

    private String username;
}
