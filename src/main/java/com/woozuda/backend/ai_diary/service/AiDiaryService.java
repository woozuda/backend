package com.woozuda.backend.ai_diary.service;

import com.woozuda.backend.account.entity.UserEntity;
import com.woozuda.backend.account.repository.UserRepository;
import com.woozuda.backend.ai_diary.dto.AiDiaryDTO;
import com.woozuda.backend.ai_diary.dto.AiDiaryResponseDTO;
import com.woozuda.backend.ai_diary.entity.AiDiary;
import com.woozuda.backend.ai_diary.repository.AiDiaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AiDiaryService {
    private final AiDiaryRepository aiDiaryRepository; // AiDiaryRepository 주입
    private final UserRepository userRepository;

    /**
     * AI 분석 결과를 DB에 저장
     *
     * @param aiDiaryDTO 분석된 일기 DTO
     */
    public void saveAiDiary(AiDiaryDTO aiDiaryDTO) {
        //유저 아이디 찾고 !
        UserEntity userEntity = userRepository.findByUsername(aiDiaryDTO.getUsername());
        // 분석 결과와 유저 아이디 저장하고!
        AiDiary aiDiarydate = AiDiary.toEntity(aiDiaryDTO ,userEntity) ;
        aiDiaryRepository.save(aiDiarydate);
    }
    public AiDiaryResponseDTO getAiDiaryByDateRangeAndId(LocalDate startDate, LocalDate endDate, Long id, String username) {
        AiDiary aiDiary = aiDiaryRepository.findByAiDiary(startDate, endDate, id, username)
                .orElseThrow(() -> new IllegalArgumentException("분석 결과 없음~ "));

        // 생성자를 사용하여 DTO 객체 생성
        return new AiDiaryResponseDTO(
                aiDiary.getId(),
                aiDiary.getStart_date(),
                aiDiary.getEnd_date(),
                aiDiary.getPlace(),
                aiDiary.getActivity(),
                aiDiary.getEmotion(),
                aiDiary.getWeather(),
                aiDiary.getWeekdayAt(),
                aiDiary.getWeekendAt(),
                aiDiary.getPositive(),
                aiDiary.getDenial(),
                aiDiary.getSuggestion()
        );
    }
}
