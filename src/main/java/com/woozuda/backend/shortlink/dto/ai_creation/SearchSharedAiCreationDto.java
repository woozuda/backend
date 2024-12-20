package com.woozuda.backend.shortlink.dto.ai_creation;

import com.woozuda.backend.ai_creation.entity.CreationType;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchSharedAiCreationDto {

    private Long ai_creation_id;

    private CreationType creationType;

    private LocalDate start_date;

    private LocalDate end_date;

    private String image_url;

    private String text;
}
