package com.woozuda.backend.ai_diary.repository;

import com.woozuda.backend.ai_diary.entity.AiDiary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository

public interface AiDiaryRepository extends JpaRepository<AiDiary, Long> ,AiDiaryRepositoryCustom{
//    @Query("SELECT a FROM AiDiary a JOIN a.user u WHERE a.start_date = :start_date AND a.end_date = :end_date AND a.id = :id AND u.username = :username")
//    Optional<AiDiary> findByDateRangeAndIdAndUsername(
//            @Param("start_date") LocalDate start_date,
//            @Param("end_date") LocalDate end_date,
//            @Param("id") Long id,
//            @Param("username") String username
//    );
}
