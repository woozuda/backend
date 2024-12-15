package com.woozuda.backend.ai_recall.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Airecall_4fs_DTO {
    private String airecallType;
    private LocalDate start_date;
    private LocalDate end_date;
    private String patternAnalysis;
    private String positiveBehavior;
    private String improvementSuggest;
    private String utilizationTips;
    private String username;

}