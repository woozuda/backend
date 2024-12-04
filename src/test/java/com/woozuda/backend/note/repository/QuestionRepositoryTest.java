package com.woozuda.backend.note.repository;

import com.woozuda.backend.note.entity.Question;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional
class QuestionRepositoryTest {

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    TestEntityManager em;

    @DisplayName("오늘의 질문 조회")
    @Test
    void retrieveTodayQuestion() throws Exception {
        // given
        Question question1 = makeQuestion("오늘의 질문1");
        em.persist(question1);

        em.flush();
        em.clear();

        //when
        Question todayQuestion = questionRepository.findByTodayDate(LocalDate.now());

        // then
        assertThat(todayQuestion).isNotNull();
        assertThat(todayQuestion.getContent()).isEqualTo(question1.getContent());
    }

    private Question makeQuestion(String questionContent) throws NoSuchFieldException, IllegalAccessException {
        Question question = new Question();

        Field content = Question.class.getDeclaredField("content");
        content.setAccessible(true);
        content.set(question, questionContent);

        return question;
    }

}