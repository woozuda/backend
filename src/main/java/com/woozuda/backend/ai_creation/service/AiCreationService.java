package com.woozuda.backend.ai_creation.service;

import com.woozuda.backend.account.entity.UserEntity;
import com.woozuda.backend.account.repository.UserRepository;
import com.woozuda.backend.ai_creation.dto.AiCreationDTO;
import com.woozuda.backend.ai_creation.entity.AiCreation;
import com.woozuda.backend.ai_creation.repository.AiCreationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Service
@RequiredArgsConstructor
public class AiCreationService {
    private final AiCreationRepository aiCreationRepository;
    private final UserRepository userRepository;

    // 저장
    public void saveCreation(AiCreationDTO aiCreationDTO) {
        UserEntity userEntity = userRepository.findByUsername(aiCreationDTO.getUsername());
        AiCreation aiCreation = AiCreation.toCreationEntity(aiCreationDTO, userEntity);
        aiCreationRepository.save(aiCreation);
    }
}
