package com.woozuda.backend.ai_recall.repository.ktp;

import com.woozuda.backend.ai_recall.entity.Airecall_4fs;
import com.woozuda.backend.ai_recall.entity.Airecall_ktp;

import java.time.LocalDate;
import java.util.Optional;

public interface AiRecallktpRpositoryCustom {
    Optional<Airecall_ktp> findByAirecallktp(LocalDate startDate, LocalDate endDate, Long airId, String username);
}
