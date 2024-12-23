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

public interface AiDiaryRepository extends JpaRepository<AiDiary, Long> , AiDiaryRepositoryCustom {

}
