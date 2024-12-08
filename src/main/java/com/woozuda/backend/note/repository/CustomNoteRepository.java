package com.woozuda.backend.note.repository;

import com.woozuda.backend.note.dto.request.NoteCondRequestDto;
import com.woozuda.backend.note.dto.response.NoteResponseDto;

import java.util.List;

//TODO search...Note() 메서드 공통: noteId 만으로 해당 노트가 로그인한 사용자의 것인지 보장할 수 없음 -> 추가 로직 구현
public interface CustomNoteRepository {

    List<NoteResponseDto> searchCommonNoteList(List<Long> idList, NoteCondRequestDto condition);

    List<NoteResponseDto> searchQuestionNoteList(List<Long> idList, NoteCondRequestDto condition);

    List<NoteResponseDto> searchRetrospectiveNoteList(List<Long> idList, NoteCondRequestDto condition);

    NoteResponseDto searchCommonNote(Long noteId);

    NoteResponseDto searchQuestionNote(Long noteId);

    NoteResponseDto searchRetrospectiveNote(Long noteId);
}

