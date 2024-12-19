package com.woozuda.backend.ai_recall.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Airecall_Kpt_DTO {
    private String airecallType;
    private LocalDate start_date;
    private LocalDate end_date;
    private String strength_analysis;
    private String improvement;
    private String scalability;
    private String username;
}
