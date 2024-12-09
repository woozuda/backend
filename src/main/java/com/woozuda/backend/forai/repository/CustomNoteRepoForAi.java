package com.woozuda.backend.forai.repository;

import com.woozuda.backend.forai.repository.dto.NonRetroNoteEntryResponseDto;
import com.woozuda.backend.forai.repository.dto.RetroNoteEntryResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface CustomNoteRepoForAi {

    List<NonRetroNoteEntryResponseDto> searchNonRetroNote(String username, LocalDate startDate, LocalDate endDate);

    List<RetroNoteEntryResponseDto> searchRetroNote(String username, LocalDate startDate, LocalDate endDate);

}
