package com.woozuda.backend.note.service;

import com.woozuda.backend.account.entity.UserEntity;
import com.woozuda.backend.diary.repository.DiaryRepository;
import com.woozuda.backend.note.dto.request.NoteCondRequestDto;
import com.woozuda.backend.note.dto.response.NoteEntryResponseDto;
import com.woozuda.backend.note.dto.response.NoteResponseDto;
import com.woozuda.backend.note.repository.NoteRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DisplayName("노트 비즈니스 로직")
@ExtendWith(MockitoExtension.class)
class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;
    @Mock
    private DiaryRepository diaryRepository;

    @InjectMocks
    private NoteService noteService;

    UserEntity user = new UserEntity(1L, "hwang", "asdfasdf", "ROLE_USER");
    NoteCondRequestDto cond = new NoteCondRequestDto();

    NoteResponseDto commonNote1 = new NoteResponseDto(1L, "diary1", "common note1", "2024-12-04", "common weather1", "common season1", "common feeling1", List.of("common content1"));
    NoteResponseDto commonNote2 = new NoteResponseDto(2L, "diary2", "common note2", "2024-12-10", "common weather2", "common season2", "common feeling2", List.of("common content2"));

    NoteResponseDto questionNote1 = new NoteResponseDto(3L, "diary3", "question note1", "2024-12-07", "question weather1", "question season1", "question feeling1", "question1", List.of("question content1"));
    NoteResponseDto questionNote2 = new NoteResponseDto(4L, "diary2", "question note2", "2024-12-10", "question weather2", "question season2", "question feeling2", "question2", List.of("question content2"));

    NoteResponseDto retrospectiveNote1 = new NoteResponseDto(6L, "diary3", "retrospective note2", "2024-06-05", "SCS", List.of("retrospective2 content1", "retrospective2 content2", "retrospective2 content3"));
    NoteResponseDto retrospectiveNote2 = new NoteResponseDto(5L, "diary1", "retrospective note1", "2024-12-11", "FOUR_F_S", List.of("retrospective1 content1", "retrospective1 content2", "retrospective1 content3", "retrospective1 content4"));

    List<Long> idList = List.of(1L, 2L);

    @DisplayName("노트 조회 - 적합한 페이지 범위")
    @Test
    void searchNoteList_resultExist() {
        // given
        when(diaryRepository.searchDiaryIdList(user.getUsername()))
                .thenReturn(
                        idList
                );
        when(noteRepository.searchCommonNoteList(idList, cond))
                .thenReturn(
                        List.of(
                                commonNote1,
                                commonNote2
                        )
                );
        when(noteRepository.searchQuestionNoteList(idList, cond))
                .thenReturn(
                        List.of(
                                questionNote1,
                                questionNote2
                        )
                );
        when(noteRepository.searchRetrospectiveNoteList(idList, cond))
                .thenReturn(
                        List.of(
                                retrospectiveNote1,
                                retrospectiveNote2
                        )
                );

        Pageable pageable = PageRequest.of(0, 20);

        // when
        Page<NoteEntryResponseDto> noteList = noteService.getNoteList(user.getUsername(), pageable, cond);

        // then
        assertThat(noteList.getTotalPages()).isEqualTo(1);
        assertThat(noteList.getTotalElements()).isEqualTo(6);
        assertThat(noteList.getNumber()).isEqualTo(0);
        assertThat(noteList.getContent())
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactly(
                        new NoteEntryResponseDto("RETROSPECTIVE", retrospectiveNote2),
                        new NoteEntryResponseDto("COMMON", commonNote2),
                        new NoteEntryResponseDto("QUESTION", questionNote2),
                        new NoteEntryResponseDto("QUESTION", questionNote1),
                        new NoteEntryResponseDto("COMMON", commonNote1),
                        new NoteEntryResponseDto("RETROSPECTIVE", retrospectiveNote1)
                );
    }

    @DisplayName("노트 조회 - 적합하지 않은 페이지 범위 ")
    @Test
    void searchNoteList_resultNotExist() {
        // given
        when(diaryRepository.searchDiaryIdList(user.getUsername()))
                .thenReturn(
                        idList
                );
        when(noteRepository.searchCommonNoteList(idList, cond))
                .thenReturn(
                        List.of(
                                commonNote1,
                                commonNote2
                        )
                );
        when(noteRepository.searchQuestionNoteList(idList, cond))
                .thenReturn(
                        List.of(
                                questionNote1,
                                questionNote2
                        )
                );
        when(noteRepository.searchRetrospectiveNoteList(idList, cond))
                .thenReturn(
                        List.of(
                                retrospectiveNote1,
                                retrospectiveNote2
                        )
                );

        Pageable pageable = PageRequest.of(1, 6);

        // when
        Page<NoteEntryResponseDto> noteList = noteService.getNoteList(user.getUsername(), pageable, cond);

        // then
        assertThat(noteList.getContent()).isNotNull();
        assertThat(noteList.getContent()).hasSize(0);
        assertThat(noteList.getNumberOfElements()).isEqualTo(0);
    }

    @DisplayName("노트 조회 - 특정 종류의 노트가 없는 경우")
    @Test
    void searchNoteList_commonNoteNotExist() {
        // given
        when(diaryRepository.searchDiaryIdList(user.getUsername()))
                .thenReturn(
                        idList
                );
        when(noteRepository.searchCommonNoteList(idList, cond))
                .thenReturn(
                        Collections.emptyList()
                );
        when(noteRepository.searchQuestionNoteList(idList, cond))
                .thenReturn(
                        List.of(
                                questionNote1,
                                questionNote2
                        )
                );
        when(noteRepository.searchRetrospectiveNoteList(idList, cond))
                .thenReturn(
                        List.of(
                                retrospectiveNote1,
                                retrospectiveNote2
                        )
                );

        Pageable pageable = PageRequest.of(0, 20);

        // when
        Page<NoteEntryResponseDto> noteList = noteService.getNoteList(user.getUsername(), pageable, cond);

        // then
        assertThat(noteList.getTotalPages()).isEqualTo(1);
        assertThat(noteList.getTotalElements()).isEqualTo(4);
        assertThat(noteList.getNumber()).isEqualTo(0);
        assertThat(noteList.getContent())
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactly(
                        new NoteEntryResponseDto("RETROSPECTIVE", retrospectiveNote2),
                        new NoteEntryResponseDto("QUESTION", questionNote2),
                        new NoteEntryResponseDto("QUESTION", questionNote1),
                        new NoteEntryResponseDto("RETROSPECTIVE", retrospectiveNote1)
                );
    }


}