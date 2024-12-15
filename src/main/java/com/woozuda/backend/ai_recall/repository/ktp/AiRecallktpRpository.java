package com.woozuda.backend.ai_recall.repository.ktp;

import com.woozuda.backend.ai_recall.entity.Airecall_ktp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface AiRecallktpRpository extends JpaRepository<Airecall_ktp, Long> , AiRecallktpRpositoryCustom {

}
