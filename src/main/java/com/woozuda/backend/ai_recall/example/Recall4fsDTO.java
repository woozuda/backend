package com.woozuda.backend.ai_recall.example;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class Recall4fsDTO {
    private String title;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private String fact;

    private String feeling;

    private String finding;

    private String futureaction;

}
