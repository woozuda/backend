package com.woozuda.backend.forai.repository;

import com.woozuda.backend.forai.dto.NonRetroNoteEntryResponseDto;
import com.woozuda.backend.forai.dto.RetroNoteEntryResponseDto;
import com.woozuda.backend.note.entity.type.Framework;

import java.time.LocalDate;
import java.util.List;

public interface CustomNoteRepoForAi {

    List<NonRetroNoteEntryResponseDto> searchNonRetroNote(String username, LocalDate startDate, LocalDate endDate);

    List<RetroNoteEntryResponseDto> searchRetroNote(String username, LocalDate startDate, LocalDate endDate , Framework type);

}
