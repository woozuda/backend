package com.woozuda.backend.ai_diary.repository;

import com.woozuda.backend.ai_diary.entity.AiDiary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AiDiaryRepository extends JpaRepository<AiDiary, Long> {

}
