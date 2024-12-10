package com.woozuda.backend.ai_recall.example;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Recall4fsDTOList {
    private String type;
    @JsonProperty("4fs")
    private List<Recall4fsDTO> recall4fsdto;// 일기 목록

}
