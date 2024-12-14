package com.woozuda.backend.ai_recall.repository;

import com.woozuda.backend.ai_recall.entity.Airecall_4fs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface AiRecall4fsRpository extends JpaRepository<Airecall_4fs, Long> {
    @Query("SELECT p, ac.start_date, ac.end_date " +
            "FROM Airecall_4fs p " +
            "JOIN Airecall ac ON p.air_id = ac.air_id " +
            "JOIN UserEntity u ON ac.user.id = u.id " +
            "WHERE ac.type = '4FS' " +
            "AND ac.start_date BETWEEN :startDate AND :endDate " +
            "AND ac.end_date BETWEEN :startDate AND :endDate " +
            "AND ac.air_id = :airId " +
            "AND u.username = :username")
    Optional<Airecall_4fs> findByAirecall4FSTypeAndDateRangeAndUserId(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("airId") Long airId,
            @Param("username") String username);
}
