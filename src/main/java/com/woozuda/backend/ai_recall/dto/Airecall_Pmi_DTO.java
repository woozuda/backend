package com.woozuda.backend.ai_recall.dto;

import com.woozuda.backend.ai_recall.entity.AirecallType;
import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDate;

@Data
public class Airecall_Pmi_DTO {
    private AirecallType airecallType;
    private LocalDate start_date;
    private LocalDate end_date;
    private String strength_analysis;
    private String improvement;
    private String scalability;
    private String username;
}
