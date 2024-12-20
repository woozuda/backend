package com.woozuda.backend.ai_recall.repository.kpt;

import com.woozuda.backend.ai_recall.entity.Airecall_kpt;

import java.time.LocalDate;
import java.util.Optional;

public interface AiRecallkptRpositoryCustom {
    Optional<Airecall_kpt> findByAirecallktp(LocalDate startDate, LocalDate endDate, String username);
}
