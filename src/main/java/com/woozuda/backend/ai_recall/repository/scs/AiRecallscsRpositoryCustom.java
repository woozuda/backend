package com.woozuda.backend.ai_recall.repository.scs;

import com.woozuda.backend.ai_recall.entity.Airecall_pmi;
import com.woozuda.backend.ai_recall.entity.Airecall_scs;

import java.time.LocalDate;
import java.util.Optional;

public interface AiRecallscsRpositoryCustom {
    Optional<Airecall_scs> findByAirecallscs(LocalDate startDate, LocalDate endDate, Long airId, String username);

}
