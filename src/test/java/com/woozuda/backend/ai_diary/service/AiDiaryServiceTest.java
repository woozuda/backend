package com.woozuda.backend.ai_diary.service;

import com.woozuda.backend.ai_diary.dto.AiDiaryDTO;
import com.woozuda.backend.ai_diary.entity.AiDiary;
import com.woozuda.backend.ai_diary.repository.AiDiaryRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class AiDiaryServiceTest {
    private static final Logger logger = LoggerFactory.getLogger(AiDiaryServiceTest.class);

    @Autowired
    private AiDiaryService aiDiaryService;

    @Autowired
    private AiDiaryRepository aiDiaryRepository;

    //@Test
    public void testSaveAiDiary() {
        // given: test data 준비
        AiDiaryDTO aiDiaryDTO = new AiDiaryDTO(
                "Seoul", "Jogging", "Happy", "Sunny",
                0.6f, 0.4f, 0.75f, 0.25f, "Keep up the good work!"
        );

        AiDiary aiDiary = AiDiary.toEntity(aiDiaryDTO);
        logger.info("테스트 결과값 전 : {}", aiDiaryDTO);

        // when: 서비스 메소드 호출
        AiDiary savedAiDiary = aiDiaryService.saveAiDiary(aiDiaryDTO);

        // then: 결과 검증
        assertNotNull(savedAiDiary);
        logger.info("아이디 확인: {}", savedAiDiary.getId());
        assertNotNull(savedAiDiary.getId());  // ID가 생성되었는지 확인

        // DB에 실제로 저장된 값이 맞는지 확인하기 위해 DB에서 해당 엔티티를 조회
        AiDiary foundAiDiary = aiDiaryRepository.findById(savedAiDiary.getId()).orElse(null);
        assertNotNull(foundAiDiary);
        logger.info("잘 들어갔는 DB조회: {}", foundAiDiary);

        // DB에서 가져온 값이 예상한 값과 일치하는지 확인
        assertEquals(aiDiaryDTO.getPlace(), foundAiDiary.getPlace());
        assertEquals(aiDiaryDTO.getActivity(), foundAiDiary.getActivity());
        assertEquals(aiDiaryDTO.getEmotion(), foundAiDiary.getEmotion());
        assertEquals(aiDiaryDTO.getWeather(), foundAiDiary.getWeather());
        assertEquals(aiDiaryDTO.getWeekdayAt(), foundAiDiary.getWeekdayAt());
        assertEquals(aiDiaryDTO.getWeekendAt(), foundAiDiary.getWeekendAt());
        assertEquals(aiDiaryDTO.getPositive(), foundAiDiary.getPositive());
        assertEquals(aiDiaryDTO.getDenial(), foundAiDiary.getDenial());
        assertEquals(aiDiaryDTO.getSuggestion(), foundAiDiary.getSuggestion());


    // 삭제 후 DB에서 해당 데이터가 없어진 것을 확인
        aiDiaryRepository.delete(foundAiDiary);
        AiDiary deletedAiDiary = aiDiaryRepository.findById(foundAiDiary.getId()).orElse(null);
        assertNull(deletedAiDiary);  // 삭제 후 null이어야 함

        logger.info("AiDiary deleted successfully with ID: {}", savedAiDiary.getId());
    }
}