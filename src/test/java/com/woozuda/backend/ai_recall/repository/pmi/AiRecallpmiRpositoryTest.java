package com.woozuda.backend.ai_recall.repository.pmi;

import com.woozuda.backend.account.entity.AiType;
import com.woozuda.backend.account.entity.UserEntity;
import com.woozuda.backend.account.repository.UserRepository;
import com.woozuda.backend.ai_recall.dto.Airecall_Pmi_DTO;


import com.woozuda.backend.ai_recall.entity.Airecall_pmi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AiRecallpmiRpositoryTest {
    @Autowired
    private AiRecallpmiRpository aiRecallpmiRpository;

    @Autowired
    private UserRepository userRepository;


    UserEntity user;
    Airecall_pmi airecall_pmi;

    @BeforeEach
    void setUp() {
        // User 엔티티 준비 및 저장
        user = new UserEntity(null, "sun", "sun0304@", "ROLE_USER", AiType.PICTURE_NOVEL, true, "sun@gmail.com", "woozuda");
        userRepository.save(user);

        // AiDiaryDTO 객체 생성
        Airecall_Pmi_DTO airecall_pmi_dto  = new Airecall_Pmi_DTO(
                "pmi",
                LocalDate.of(2024, 2, 1),
                LocalDate.of(2024, 2, 7),
                "positive",
                "minus",
                "interesting",
                "conclusion_action",
                user.getUsername()
        );

        // DTO를 엔티티로 변환 후 저장
        airecall_pmi = Airecall_pmi.toairecallpmiEntity(airecall_pmi_dto, user);
        airecall_pmi = aiRecallpmiRpository.save(airecall_pmi);
    }

    @DisplayName("AI PMI 분석 결과 보기")
    @Test
    void pmi리포트분석() {
        // 저장된 데이터 조회
        Airecall_pmi found = aiRecallpmiRpository.findById(airecall_pmi.getAir_id())
                .orElseThrow(() -> new RuntimeException("데이터를 찾을 수 없습니다."));

        // 검증
        assertNotNull(found, "저장된 데이터가 null입니다.");
        assertEquals("pmi", found.getType(), "SCS 타입이 다릅니다.");
        assertEquals(airecall_pmi.getStart_date(), found.getStart_date(), "시작 날짜가 일치하지 않습니다.");
        assertEquals(airecall_pmi.getEnd_date(), found.getEnd_date(), "끝 날짜가 일치하지 않습니다.");

    }
}