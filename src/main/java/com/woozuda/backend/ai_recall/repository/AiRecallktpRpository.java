package com.woozuda.backend.ai_recall.repository;

import com.woozuda.backend.ai_recall.entity.Airecall_4fs;
import com.woozuda.backend.ai_recall.entity.Airecall_ktp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface AiRecallktpRpository extends JpaRepository<Airecall_ktp, Long> {
    @Query("SELECT a FROM Airecall_ktp a " +
            "JOIN Airecall ac ON a.air_id = ac.air_id " +
            "JOIN ac.user u " +  // 유저와 조인
            "WHERE a.type = 'KTP' " +
            "AND ac.start_date BETWEEN :start_date AND :end_date " +
            "AND ac.end_date BETWEEN :start_date AND :end_date " +
            "AND ac.air_id = :air_id " +
            "AND u.username = :username")
    Optional<Airecall_ktp> findByAirecallTypeKTPAndDateRangeAndUserId(
            @Param("start_date") LocalDate startDate,
            @Param("end_date") LocalDate endDate,
            @Param("air_id") Long air_id,
            @Param("username") String username);
}
