package com.woozuda.backend.shortlink.dto.ai_creation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SharedAiByDate {
    private LocalDate start_date;
    private List<SharedAiByType> AiCreations;
}
