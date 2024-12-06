package com.woozuda.backend.note.repository;

import com.woozuda.backend.note.dto.request.NoteCondRequestDto;
import com.woozuda.backend.note.dto.response.NoteResponseDto;

import java.util.List;

public interface CustomNoteRepository {

    List<NoteResponseDto> searchCommonNoteList(String username, NoteCondRequestDto condition);

    List<NoteResponseDto> searchQuestionNoteList(String username, NoteCondRequestDto condition);

    List<NoteResponseDto> searchRetrospectiveNoteList(String username, NoteCondRequestDto condition);
}

