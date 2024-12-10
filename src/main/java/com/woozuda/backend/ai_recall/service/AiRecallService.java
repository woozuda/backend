package com.woozuda.backend.ai_recall.service;

import com.woozuda.backend.account.entity.UserEntity;
import com.woozuda.backend.account.repository.UserRepository;
import com.woozuda.backend.ai_recall.dto.Airecall_4fs_DTO;
import com.woozuda.backend.ai_recall.entity.Airecall_4fs;
import com.woozuda.backend.ai_recall.repository.AiRecallRpository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

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

    public Optional<Airecall_4fs> getAirecall4fs(LocalDate start_date, LocalDate end_date, Long air_id) {
        return aiRecallRpository.findByAirecallTypeAndDateRange(start_date, end_date, air_id);
    }
}
