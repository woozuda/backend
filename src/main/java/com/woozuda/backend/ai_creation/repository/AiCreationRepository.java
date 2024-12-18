package com.woozuda.backend.ai_creation.repository;

import com.woozuda.backend.ai_creation.entity.AiCreation;
import com.woozuda.backend.ai_diary.entity.AiDiary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AiCreationRepository  extends JpaRepository<AiCreation, Long> , AiCreationRepositoryCustom{
}
