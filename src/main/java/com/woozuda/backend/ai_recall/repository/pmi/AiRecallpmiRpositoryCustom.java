package com.woozuda.backend.ai_recall.repository.pmi;

import com.woozuda.backend.ai_recall.entity.Airecall_pmi;

import java.time.LocalDate;
import java.util.Optional;

public interface AiRecallpmiRpositoryCustom {
    Optional<Airecall_pmi> findByAirecallpmi(LocalDate startDate, LocalDate endDate, String username);

}
