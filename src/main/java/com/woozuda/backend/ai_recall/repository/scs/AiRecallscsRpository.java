package com.woozuda.backend.ai_recall.repository.scs;

import com.woozuda.backend.ai_recall.entity.Airecall_scs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface AiRecallscsRpository extends JpaRepository<Airecall_scs, Long>, AiRecallscsRpositoryCustom {

}
