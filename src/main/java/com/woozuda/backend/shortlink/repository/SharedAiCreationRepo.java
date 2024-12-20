package com.woozuda.backend.shortlink.repository;

import com.woozuda.backend.note.entity.Note;
import com.woozuda.backend.shortlink.dto.ai_creation.SearchSharedAiCreationDto;
import com.woozuda.backend.shortlink.dto.note.SharedNoteDto;

import java.util.List;

public interface SharedAiCreationRepo {
    List<SearchSharedAiCreationDto> searchSharedAiCreation(String username);
}
