package com.woozuda.backend.ai_diary.repository;

import com.woozuda.backend.ai_diary.entity.AiDiary;

import java.time.LocalDate;
import java.util.Optional;

public interface AiDiaryRepositoryCustom {
    Optional<AiDiary> findByAiDiary(LocalDate start_date, LocalDate end_date, String username);
}
