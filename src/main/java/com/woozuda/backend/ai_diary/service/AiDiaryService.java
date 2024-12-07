package com.woozuda.backend.ai_diary.service;

import com.woozuda.backend.ai_diary.dto.AiDiaryDTO;
import com.woozuda.backend.ai_diary.entity.AiDiary;
import com.woozuda.backend.ai_diary.repository.AiDiaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AiDiaryService {
    private final AiDiaryRepository aiDiaryRepository; // AiDiaryRepository 주입

    /**
     * AI 분석 결과를 DB에 저장
     * @param aiDiaryDTO 분석된 일기 DTO
     * @return 저장된 AiDiary 엔티티
     */
    public AiDiary saveAiDiary(AiDiaryDTO aiDiaryDTO) {
        AiDiary aiDiarydate = AiDiary.toEntity(aiDiaryDTO);
        return aiDiaryRepository.save(aiDiarydate); // 저장 후 반환
    }
    public List<AiDiary> getAiDiariesByDateRangeAndId(LocalDate startDate, LocalDate endDate, Long id) {
        return aiDiaryRepository.findByDateRangeAndId(startDate, endDate, id);
    }
}
