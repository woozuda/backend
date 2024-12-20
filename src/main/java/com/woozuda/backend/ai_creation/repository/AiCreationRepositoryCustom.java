package com.woozuda.backend.ai_creation.repository;

import com.woozuda.backend.ai_creation.entity.AiCreation;
import com.woozuda.backend.ai_diary.entity.AiDiary;

import java.time.LocalDate;
import java.util.Optional;

public interface AiCreationRepositoryCustom {
    Optional<AiCreation> findByAiCreation(LocalDate start_date, LocalDate end_date, String username);
}
