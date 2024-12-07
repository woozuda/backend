package com.woozuda.backend.ai_diary.repository;

import com.woozuda.backend.ai_diary.entity.AiDiary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AiDiaryRepository extends JpaRepository<AiDiary, Long> {
    // startDate와 endDate 사이의 데이터를 조회, id 포함
    @Query("SELECT a FROM AiDiary a WHERE a.startDate BETWEEN :startDate AND :endDate AND a.endDate BETWEEN :startDate AND :endDate AND a.id = :id")
    List<AiDiary> findByDateRangeAndId(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("id") Long id);

}
