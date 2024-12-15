package com.woozuda.backend.ai_recall.repository.pmi;

import com.woozuda.backend.ai_recall.entity.Airecall_pmi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface AiRecallpmiRpository extends JpaRepository<Airecall_pmi, Long> , AiRecallpmiRpositoryCustom {

}
