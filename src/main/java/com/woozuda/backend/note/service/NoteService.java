package com.woozuda.backend.note.service;

import com.woozuda.backend.diary.dto.response.SingleDiaryResponseDto;
import com.woozuda.backend.note.dto.response.NoteDetailResponseDto;
import com.woozuda.backend.note.dto.response.NoteEntryResponseDto;
import com.woozuda.backend.note.dto.response.NoteListResponseDto;
import com.woozuda.backend.note.dto.response.NoteResponseDto;
import com.woozuda.backend.note.dto.response.NoteSummaryResponseDto;
import com.woozuda.backend.note.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;

    //최신순 일기 조회
    public NoteListResponseDto getNoteList(String username) {
        return null;
    }

    /*
     * 날짜별 일기 조회
     *
     * 1. UserEntity, Diary, Note를 조인하여 username 사용자가 date 에 작성한 노트 요약 정보 조회
     *       => 노트 요약 정보: type, note id, diary title, note title, date
     * 2. 각 노트 정보마다, CommonNote, QuestionNote + Question, RetrospectiveNote 중 알맞은 엔티티와 NoteContent 엔티티를 조인하여 노트 세부 정보 조회
     *      => 노트 세부 정보: weather, feeling, season, question, content 등
     * 3. 일차 정보와 이차 정보를 결합하여 NoteEntryResponseDto 생성
     */
    public NoteListResponseDto getNoteList(String username, LocalDate date) {
        List<NoteEntryResponseDto> result = new ArrayList<>();

        List<NoteSummaryResponseDto> summaryDtoList = noteRepository.searchNoteSummary(username, date);
        for (NoteSummaryResponseDto summaryDto : summaryDtoList) {
            NoteDetailResponseDto detailDto;
            if (summaryDto.getType().equals("COMMON")) {
                detailDto = noteRepository.searchCommonNoteDetail(summaryDto.getNoteId());
            } else if (summaryDto.getType().equals("QUESTION")) {
                detailDto = noteRepository.searchQuestionNoteDetail(summaryDto.getNoteId());
            } else {
                detailDto = noteRepository.searchRetrospectiveNoteDetail(summaryDto.getNoteId());
            }

            if (detailDto != null) {
                result.add(NoteEntryResponseDto.of(summaryDto, detailDto));
            }
        }

        Collections.sort(result);
        return new NoteListResponseDto(result);
    }
}
