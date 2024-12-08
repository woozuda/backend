package com.woozuda.backend.note.repository;

import com.woozuda.backend.note.dto.request.NoteCondRequestDto;
import com.woozuda.backend.note.dto.response.NoteResponseDto;

import java.util.List;

public interface CustomNoteRepository {

    List<NoteResponseDto> searchCommonNoteList(List<Long> idList, NoteCondRequestDto condition);

    List<NoteResponseDto> searchQuestionNoteList(List<Long> idList, NoteCondRequestDto condition);

    List<NoteResponseDto> searchRetrospectiveNoteList(List<Long> idList, NoteCondRequestDto condition);
}

