package com.woozuda.backend.note.repository;

import com.woozuda.backend.note.dto.response.NoteDetailResponseDto;
import com.woozuda.backend.note.dto.response.NoteSummaryResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface CustomNoteRepository {

    List<NoteSummaryResponseDto> searchNoteSummary(String username, LocalDate date);

    NoteDetailResponseDto searchCommonNoteDetail(Long noteId);

    NoteDetailResponseDto searchQuestionNoteDetail(Long noteId);

    NoteDetailResponseDto searchRetrospectiveNoteDetail(Long noteId);
}

