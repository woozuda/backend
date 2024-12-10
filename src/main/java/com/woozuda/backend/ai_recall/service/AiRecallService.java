package com.woozuda.backend.ai_recall.service;

import com.woozuda.backend.account.entity.UserEntity;
import com.woozuda.backend.account.repository.UserRepository;
import com.woozuda.backend.ai_recall.dto.Airecall_4fs_DTO;
import com.woozuda.backend.ai_recall.dto.Airecall_4fs_ResponseDTO;
import com.woozuda.backend.ai_recall.entity.Airecall_4fs;
import com.woozuda.backend.ai_recall.repository.AiRecallRpository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class AiRecallService {

    private final AiRecallRpository aiRecallRpository;
    private final UserRepository userRepository;

    public void saveAirecall(Airecall_4fs_DTO airecall_4fs_dto) {
        UserEntity username = userRepository.findByUsername(airecall_4fs_dto.getUsername());
        Airecall_4fs airecallEntity = Airecall_4fs.toairecall4fsEntity(airecall_4fs_dto , username);

        aiRecallRpository.save(airecallEntity);
        log.info("Airecall 저장 완료: {}", airecallEntity);
    }

    public Airecall_4fs_ResponseDTO getAirecall4fs(LocalDate start_date, LocalDate end_date, Long id, String username) {
        // Airecall_4fs 정보를 가져옴
        Airecall_4fs airecall_4fs = aiRecallRpository.findByAirecallTypeAndDateRangeAndUserId(start_date, end_date, id, username)
                .orElseThrow(() -> new IllegalArgumentException("분석 결과 없음~"));
        return  new Airecall_4fs_ResponseDTO(
                airecall_4fs.getStart_date(),
                airecall_4fs.getEnd_date(),
                airecall_4fs.getPatternAnalysis(),
                airecall_4fs.getPositiveBehavior(),
                airecall_4fs.getImprovementSuggest(),
                airecall_4fs.getUtilizationTips()
        );
    }

}
