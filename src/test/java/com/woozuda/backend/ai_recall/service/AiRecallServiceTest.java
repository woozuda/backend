package com.woozuda.backend.ai_recall.service;

import com.woozuda.backend.ai_recall.entity.Airecall_4fs;
import com.woozuda.backend.ai_recall.entity.QAirecall_4fs;
import com.woozuda.backend.ai_recall.repository.AiRecallRepositoryImpl;
import com.woozuda.backend.ai_recall.repository.AiRecallRpository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AiRecallServiceTest {
    private static final Logger logger = LoggerFactory.getLogger(AiRecallServiceTest.class);
    //
//    @MockBean
//    private AiRecallRpository aiRecallRpository;
//
//    private LocalDate startDate;
//    private LocalDate endDate;
//    private Long id;
//
//    @Autowired
//    private AiRecallService aiRecallService;
//
//    @BeforeEach
//    public void setup() {
//        startDate = LocalDate.of(2024, 1, 1);
//        endDate = LocalDate.of(2024, 12, 31);
//        id = 1L; // 테스트용 ID 설정
//    }
//    //@Test
//    public void testGetAirecall4fsByDateRangeAndId() {
//        // Given: 데이터 준비
//        Airecall_4fs mockResult = new Airecall_4fs();
//        mockResult.setAir_id(id);
//        mockResult.setPatternAnalysis("Pattern Analysis Example");
//        mockResult.setPositiveBehavior("Positive Behavior Example");
//        mockResult.setImprovementSuggest("Improvement Suggestion Example");
//        mockResult.setUtilizationTips("Utilization Tips Example");
//
//        // When: Repository 메서드 호출 시 mockResult 반환
//        Mockito.when(aiRecallRpository.findByAirecall4fs(startDate, endDate, id)).thenReturn(Optional.of(mockResult));
//
//        // Then: 메서드 실행 결과 확인
//        Optional<Airecall_4fs> result = aiRecallService.getAirecall4fsByDateRangeAndId(startDate, endDate, id);
//
//        // Optional의 값이 존재하는지 확인
//        assertTrue(result.isPresent());
//        Airecall_4fs airecall_4fs = result.get();
//        logger.info("Pattern Analysis: {}", airecall_4fs.getPatternAnalysis());
//        logger.info("Positive Behavior: {}", airecall_4fs.getPositiveBehavior());
//        logger.info("Improvement Suggestion: {}", airecall_4fs.getImprovementSuggest());
//        logger.info("Utilization Tips: {}", airecall_4fs.getUtilizationTips());
//
//        // Optional 안의 값이 예상대로인지 확인
//        assertEquals("Pattern Analysis Example", airecall_4fs.getPatternAnalysis());
//        assertEquals("Positive Behavior Example", airecall_4fs.getPositiveBehavior());
//        assertEquals("Improvement Suggestion Example", airecall_4fs.getImprovementSuggest());
//        assertEquals("Utilization Tips Example", airecall_4fs.getUtilizationTips());
//    }

}