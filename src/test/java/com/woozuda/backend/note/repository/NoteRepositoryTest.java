package com.woozuda.backend.note.repository;

import com.woozuda.backend.account.entity.UserEntity;
import com.woozuda.backend.diary.entity.Diary;
import com.woozuda.backend.note.dto.response.NoteDetailResponseDto;
import com.woozuda.backend.note.dto.response.NoteSummaryResponseDto;
import com.woozuda.backend.note.entity.CommonNote;
import com.woozuda.backend.note.entity.NoteContent;
import com.woozuda.backend.note.entity.Question;
import com.woozuda.backend.note.entity.QuestionNote;
import com.woozuda.backend.note.entity.RetrospectiveNote;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static com.woozuda.backend.note.entity.type.Feeling.*;
import static com.woozuda.backend.note.entity.type.Framework.*;
import static com.woozuda.backend.note.entity.type.Season.*;
import static com.woozuda.backend.note.entity.type.Visibility.*;
import static com.woozuda.backend.note.entity.type.Weather.*;
import static java.time.Month.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional
class NoteRepositoryTest {

    @Autowired
    NoteRepository noteRepository;
    @Autowired
    TestEntityManager em;

    @DisplayName("노트 요약 정보 조회")
    @Test
    void searchNoteSummaryTest() {
        // given
        UserEntity user = new UserEntity();
        user.setUsername("hwang");
        user.setPassword("asdf");
        user.setRole("ROLE_USER");
        em.persist(user);

        Diary diary1 = Diary.of(user, "asdf", "Diary title1");
        Diary diary2 = Diary.of(user, "asdf", "Diary title2");
        em.persist(diary1);
        em.persist(diary2);

        Question question1 = Question.of("오늘의 질문1");
        Question question2 = Question.of("오늘의 질문2");
        em.persist(question1);
        em.persist(question2);

        LocalDate date = LocalDate.of(2024, DECEMBER, 5);
        LocalDate anotherDate = LocalDate.of(2024, DECEMBER, 4);

        CommonNote commonNote1 = CommonNote.of(diary1, "Common Note Title1", date, PRIVATE, CONTENT, SUNNY, WINTER);
        CommonNote commonNote2 = CommonNote.of(diary1, "Common Note Title2", anotherDate, PRIVATE, JOY, SNOW, WINTER);
        QuestionNote questionNote1 = QuestionNote.of(diary2, "Question Note Title1", date, PUBLIC, question1, ANGER, CLOUDY, FALL);
        QuestionNote questionNote2 = QuestionNote.of(diary2, "Question Note Title2", anotherDate, PRIVATE, question2, TIREDNESS, THUNDERSTORM, WINTER);
        RetrospectiveNote retrospectiveNote1 = RetrospectiveNote.of(diary1, "Retrospective Note Title1", anotherDate, PRIVATE, FOUR_F_S);
        RetrospectiveNote retrospectiveNote2 = RetrospectiveNote.of(diary2, "Retrospective Note Title2", date, PRIVATE, SCS);
        em.persist(commonNote1);
        em.persist(commonNote2);
        em.persist(questionNote1);
        em.persist(questionNote2);
        em.persist(retrospectiveNote1);
        em.persist(retrospectiveNote2);

        em.flush();
        em.clear();

        // when
        List<NoteSummaryResponseDto> summaryDtoList = noteRepository.searchNoteSummary(user.getUsername(), date);

        // then
        assertThat(summaryDtoList).hasSize(3);
        assertThat(summaryDtoList)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(
                        new NoteSummaryResponseDto("COMMON", commonNote1.getId(), diary1.getTitle(), commonNote1.getTitle(), date),
                        new NoteSummaryResponseDto("QUESTION", questionNote1.getId(), diary2.getTitle(), questionNote1.getTitle(), date),
                        new NoteSummaryResponseDto("RETROSPECTIVE", retrospectiveNote2.getId(), diary2.getTitle(), retrospectiveNote2.getTitle(), date)
                );
    }

    @DisplayName("노트 상세 정보 조회 - 자유 일기")
    @Test
    void searchCommonNoteDetailTest() {
        // given
        UserEntity user = new UserEntity();
        user.setUsername("hwang");
        user.setPassword("asdf");
        user.setRole("ROLE_USER");
        em.persist(user);

        Diary diary1 = Diary.of(user, "asdf", "Diary title1");
        em.persist(diary1);

        LocalDate date = LocalDate.of(2024, DECEMBER, 5);

        CommonNote commonNote1 = CommonNote.of(diary1, "Common Note Title1", date, PRIVATE, CONTENT, SUNNY, WINTER);
        em.persist(commonNote1);

        NoteContent noteContent = NoteContent.of(commonNote1, 1, "Common Note1 Content");
        em.persist(noteContent);

        em.flush();
        em.clear();

        // when
        NoteDetailResponseDto responseDto = noteRepository.searchCommonNoteDetail(commonNote1.getId());

        // then
        assertThat(responseDto).isNotNull();
        assertThat(responseDto).usingRecursiveComparison()
                .isEqualTo(new NoteDetailResponseDto(
                        commonNote1.getWeather().name(),
                        commonNote1.getFeeling().name(),
                        commonNote1.getSeason().name(),
                        null,
                        null,
                        List.of(noteContent.getContent())
                ));
    }

    @DisplayName("노트 상세 정보 조회 - 오늘의 질문 일기")
    @Test
    void searchQuestionNoteDetailTest() {
        // given
        UserEntity user = new UserEntity();
        user.setUsername("hwang");
        user.setPassword("asdf");
        user.setRole("ROLE_USER");
        em.persist(user);

        Diary diary1 = Diary.of(user, "asdf", "Diary title1");
        em.persist(diary1);

        Question question1 = Question.of("오늘의 질문1");
        em.persist(question1);

        LocalDate date = LocalDate.of(2024, DECEMBER, 5);

        QuestionNote questionNote1 = QuestionNote.of(diary1, "Question Note Title1", date, PUBLIC, question1, ANGER, CLOUDY, FALL);
        em.persist(questionNote1);

        NoteContent noteContent = NoteContent.of(questionNote1, 1, "Question Note1 Content");
        em.persist(noteContent);

        em.flush();
        em.clear();

        // when
        NoteDetailResponseDto responseDto = noteRepository.searchQuestionNoteDetail(questionNote1.getId());

        // then
        assertThat(responseDto).isNotNull();
        assertThat(responseDto).usingRecursiveComparison()
                .isEqualTo(new NoteDetailResponseDto(
                        questionNote1.getWeather().name(),
                        questionNote1.getFeeling().name(),
                        questionNote1.getSeason().name(),
                        questionNote1.getQuestion().getContent(),
                        null,
                        List.of(noteContent.getContent())
                ));
    }

    @DisplayName("노트 상세 정보 조회 - 회고")
    @Test
    void searchRetrospectiveNoteDetailTest() {
        UserEntity user = new UserEntity();
        user.setUsername("hwang");
        user.setPassword("asdf");
        user.setRole("ROLE_USER");
        em.persist(user);

        Diary diary1 = Diary.of(user, "asdf", "Diary title1");
        em.persist(diary1);

        LocalDate date = LocalDate.of(2024, DECEMBER, 5);

        RetrospectiveNote retrospectiveNote1 = RetrospectiveNote.of(diary1, "Retrospective Note Title1", date, PRIVATE, FOUR_F_S);
        em.persist(retrospectiveNote1);

        NoteContent noteContent2 = NoteContent.of(retrospectiveNote1, 2, "Retrospective Note1 Content2");
        NoteContent noteContent1 = NoteContent.of(retrospectiveNote1, 1, "Retrospective Note1 Content1");
        NoteContent noteContent4 = NoteContent.of(retrospectiveNote1, 4, "Retrospective Note1 Content4");
        NoteContent noteContent3 = NoteContent.of(retrospectiveNote1, 3, "Retrospective Note1 Content3");
        em.persist(noteContent2);
        em.persist(noteContent1);
        em.persist(noteContent4);
        em.persist(noteContent3);

        em.flush();
        em.clear();

        // when
        NoteDetailResponseDto responseDto = noteRepository.searchRetrospectiveNoteDetail(retrospectiveNote1.getId());

        // then
        assertThat(responseDto).isNotNull();
        assertThat(responseDto).usingRecursiveComparison()
                .isEqualTo(new NoteDetailResponseDto(
                        null,
                        null,
                        null,
                        null,
                        retrospectiveNote1.getType().name(),
                        List.of(noteContent1.getContent(), noteContent2.getContent(), noteContent3.getContent(), noteContent4.getContent())
                ));
    }

}