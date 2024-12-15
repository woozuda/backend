package com.woozuda.backend.ai_recall.repository.fourfs;

import com.woozuda.backend.ai_recall.entity.Airecall_4fs;

import java.time.LocalDate;
import java.util.Optional;

public interface AiRecall4fsRpositoryCustom {
    Optional<Airecall_4fs> findByAirecall4FS( LocalDate startDate, LocalDate endDate, Long airId, String username);
}
