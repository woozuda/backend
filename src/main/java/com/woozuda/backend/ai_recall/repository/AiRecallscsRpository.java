package com.woozuda.backend.ai_recall.repository;

import com.woozuda.backend.ai_recall.entity.Airecall_4fs;
import com.woozuda.backend.ai_recall.entity.Airecall_scs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface AiRecallscsRpository extends JpaRepository<Airecall_scs, Long> {
    @Query("SELECT p, ac.start_date, ac.end_date " +
            "FROM Airecall_scs p " +
            "JOIN Airecall ac ON p.air_id = ac.air_id " +
            "JOIN UserEntity u ON ac.user.id = u.id " +
            "WHERE ac.type = 'SCS' " +
            "AND ac.start_date BETWEEN :startDate AND :endDate " +
            "AND ac.end_date BETWEEN :startDate AND :endDate " +
            "AND ac.air_id = :airId " +
            "AND u.username = :username")
    Optional<Airecall_scs> findByAirecallTypeSCSAndDateRangeAndUserId(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("airId") Long air_id,
            @Param("username") String username);
}
