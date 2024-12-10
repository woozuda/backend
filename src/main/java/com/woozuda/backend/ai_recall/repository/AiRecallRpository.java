package com.woozuda.backend.ai_recall.repository;

import com.woozuda.backend.account.entity.UserEntity;
import com.woozuda.backend.ai_diary.entity.AiDiary;
import com.woozuda.backend.ai_diary.repository.AiDiaryRepository;
import com.woozuda.backend.ai_recall.entity.Airecall;
import com.woozuda.backend.ai_recall.entity.Airecall_4fs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AiRecallRpository  extends JpaRepository<Airecall_4fs, Long> {
    @Query("SELECT a FROM Airecall_4fs a " +
            "JOIN Airecall ac ON a.air_id = ac.air_id " +
            "WHERE a.airecallType = 'FFS' " +
            "AND ac.start_date BETWEEN :start_date AND :end_date " +
            "AND ac.end_date BETWEEN :start_date AND :end_date " +
            "AND ac.air_id = :air_id")
    Optional<Airecall_4fs> findByAirecallTypeAndDateRange(
            @Param("start_date") LocalDate startDate,
            @Param("end_date") LocalDate endDate,
            @Param("air_id") Long air_id);

}
