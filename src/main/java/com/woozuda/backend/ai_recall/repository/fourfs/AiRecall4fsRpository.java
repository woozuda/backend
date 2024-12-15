package com.woozuda.backend.ai_recall.repository.fourfs;

import com.woozuda.backend.ai_recall.entity.Airecall_4fs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AiRecall4fsRpository extends JpaRepository<Airecall_4fs, Long> , AiRecall4fsRpositoryCustom {

}
