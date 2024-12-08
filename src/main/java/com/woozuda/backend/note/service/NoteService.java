package com.woozuda.backend.note.service;

import com.woozuda.backend.note.dto.request.CommonNoteSaveRequestDto;
import com.woozuda.backend.diary.dto.response.NoteIdResponseDto;
import com.woozuda.backend.diary.entity.Diary;
import com.woozuda.backend.diary.repository.DiaryRepository;
import com.woozuda.backend.note.dto.request.NoteCondRequestDto;
import com.woozuda.backend.note.dto.request.QuestionNoteSaveRequestDto;
import com.woozuda.backend.note.dto.request.RetrospectiveNoteSaveRequestDto;
import com.woozuda.backend.note.dto.response.NoteEntryResponseDto;
import com.woozuda.backend.note.dto.response.NoteResponseDto;
import com.woozuda.backend.note.entity.CommonNote;
import com.woozuda.backend.note.entity.NoteContent;
import com.woozuda.backend.note.entity.Question;
import com.woozuda.backend.note.entity.QuestionNote;
import com.woozuda.backend.note.entity.RetrospectiveNote;
import com.woozuda.backend.note.entity.type.Feeling;
import com.woozuda.backend.note.entity.type.Framework;
import com.woozuda.backend.note.entity.type.Season;
import com.woozuda.backend.note.entity.type.Weather;
import com.woozuda.backend.note.repository.NoteRepository;
import com.woozuda.backend.note.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static com.woozuda.backend.note.entity.type.Visibility.PRIVATE;

@Service
@Transactional
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private final DiaryRepository diaryRepository;

    /**
     * 최신순 일기 조회
     * <p>
     * 사용자 이름이 username 인 일기 중
     * 1. 자유 일기 관련 NoteEntryResponseDto List 조회
     * 2. 오늘의 질문 일기 관련 NoteEntryResponseDto List 조회
     * 3. 회고 관련 NoteEntryResponseDto List 조회
     * <p>
     * 3개의 List 를 묶어서 하나의 Page 로 처리
     *
     * TODO 성능 향상
     * 1. 다이어리와 사용자 테이블을 조인해서 해당 사용자가 작성한 다이어리 id 리스트를 조회하는 쿼리를 먼저 날린 다음,
     * 해당 id 리스트를 기반으로 각 일기 종류를 조회 -> user 테이블 조인 x => 적용
     * 2. createdBy 같은 기본 필드를 다이어리, 일기 등에 추가하고, 로그인한 사용자의 username 을 자동으로 저장하도록 변경
     * -> user 테이블 조인 x, 1번 방법처럼 쿼리를 하나 미리 보내지 않아도 됨
     * 3. 레디스 사용
     */
    @Transactional(readOnly = true)
    public Page<NoteEntryResponseDto> getNoteList(String username, Pageable pageable, NoteCondRequestDto condition) {
        List<Long> idList = diaryRepository.searchDiaryIdList(username);

        List<NoteResponseDto> commonNoteDtoList = noteRepository.searchCommonNoteList(idList, condition);
        List<NoteResponseDto> questionNoteDtoList = noteRepository.searchQuestionNoteList(idList, condition);
        List<NoteResponseDto> retrospectiveNoteDtoList = noteRepository.searchRetrospectiveNoteList(idList, condition);

        List<NoteEntryResponseDto> allContent = Stream.of(
                        commonNoteDtoList.stream()
                                .map(noteResponseDto -> new NoteEntryResponseDto("COMMON", noteResponseDto.convertEnum())),
                        questionNoteDtoList.stream()
                                .map(noteResponseDto -> new NoteEntryResponseDto("QUESTION", noteResponseDto.convertEnum())),
                        retrospectiveNoteDtoList.stream()
                                .map(noteResponseDto -> new NoteEntryResponseDto("RETROSPECTIVE", noteResponseDto.convertEnum()))
                ).flatMap(stream -> stream)
                .sorted(Comparator.naturalOrder())
                .toList();

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), allContent.size());

        if (start > end) {
            return new PageImpl<>(Collections.emptyList(), pageable, allContent.size());
        } else {
            return new PageImpl<>(allContent.subList(start, end), pageable, allContent.size());
        }
    }
}
