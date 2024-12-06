package com.woozuda.backend.note.service;

import com.woozuda.backend.note.dto.request.NoteCondRequestDto;
import com.woozuda.backend.note.dto.response.NoteEntryResponseDto;
import com.woozuda.backend.note.dto.response.NoteResponseDto;
import com.woozuda.backend.note.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Service
@Transactional
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;

    /**
     * 최신순 일기 조회
     * <p>
     * 사용자 이름이 username 인 일기 중
     * 1. 자유 일기 관련 NoteEntryResponseDto List 조회
     * 2. 오늘의 질문 일기 관련 NoteEntryResponseDto List 조회
     * 3. 회고 관련 NoteEntryResponseDto List 조회
     * <p>
     * 3개의 List 를 묶어서 하나의 Page 로 처리
     */
    public Page<NoteEntryResponseDto> getNoteList(String username, Pageable pageable, NoteCondRequestDto condition) {
        List<NoteResponseDto> commonNoteDtoList = noteRepository.searchCommonNoteList(username, condition);
        List<NoteResponseDto> questionNoteDtoList = noteRepository.searchQuestionNoteList(username, condition);
        List<NoteResponseDto> retrospectiveNoteDtoList = noteRepository.searchRetrospectiveNoteList(username, condition);

        List<NoteEntryResponseDto> allContent = Stream.of(
                        commonNoteDtoList.stream()
                                .map(noteResponseDto -> new NoteEntryResponseDto("COMMON", noteResponseDto)),
                        questionNoteDtoList.stream()
                                .map(noteResponseDto -> new NoteEntryResponseDto("QUESTION", noteResponseDto)),
                        retrospectiveNoteDtoList.stream()
                                .map(noteResponseDto -> new NoteEntryResponseDto("RETROSPECTIVE", noteResponseDto))
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
