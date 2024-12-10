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

}