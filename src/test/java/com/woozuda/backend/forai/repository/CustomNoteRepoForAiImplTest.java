package com.woozuda.backend.forai.repository;

import com.woozuda.backend.diary.entity.Diary;
import com.woozuda.backend.note.entity.CommonNote;
import com.woozuda.backend.note.entity.QuestionNote;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 실제 DB 사용
class CustomNoteRepoForAiImplTest {

    @Autowired
    private CustomNoteRepoForAi customNoteRepoForAi; // 테스트할 대상

    @Autowired
    private EntityManager em;

    @Test
    void testCountAiDiary() {
        // Given: 테스트 데이터 삽입
        String username = "woozuda@gmail.com";
        LocalDate startDate = LocalDate.of(2024, 12, 15);
        LocalDate endDate = LocalDate.of(2024, 12, 22);

        // When: CountAiDiary 메서드 호출
        long result = customNoteRepoForAi.aiDiaryCount(username, startDate, endDate);
        System.out.println("result " + result);
        // Then: 결과 검증
        //assertThat(result).isEqualTo(1);
    }
}