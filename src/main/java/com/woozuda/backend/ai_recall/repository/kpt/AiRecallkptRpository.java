package com.woozuda.backend.ai_recall.repository.kpt;

import com.woozuda.backend.ai_recall.entity.Airecall_kpt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AiRecallkptRpository extends JpaRepository<Airecall_kpt, Long> , AiRecallkptRpositoryCustom {

}
