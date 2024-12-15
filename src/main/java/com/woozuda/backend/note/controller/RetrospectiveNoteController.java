package com.woozuda.backend.note.controller;

import com.woozuda.backend.account.dto.CustomUser;
import com.woozuda.backend.diary.dto.response.NoteIdResponseDto;
import com.woozuda.backend.note.dto.request.RetrospectiveNoteModifyRequestDto;
import com.woozuda.backend.note.dto.request.RetrospectiveNoteSaveRequestDto;
import com.woozuda.backend.note.dto.response.NoteResponseDto;
import com.woozuda.backend.note.service.RetrospectiveNoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/note/retrospective")
@RequiredArgsConstructor
public class RetrospectiveNoteController {

    private final RetrospectiveNoteService noteService;

    @PostMapping
    public ResponseEntity<NoteIdResponseDto> createRetrospectiveNote(
            @AuthenticationPrincipal CustomUser user,
            @RequestBody @Valid RetrospectiveNoteSaveRequestDto requestDto
    ) {
        String username = user.getUsername();
        NoteIdResponseDto responseDto = noteService.saveRetrospectiveNote(username, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{noteId}")
    public ResponseEntity<NoteResponseDto> getRetrospectiveNote(
            @AuthenticationPrincipal CustomUser user,
            @PathVariable("noteId") Long noteId
    ) {
        String username = user.getUsername();
        NoteResponseDto responseDto = noteService.getRetrospectiveNote(username, noteId);
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/{noteId}")
    public ResponseEntity<NoteIdResponseDto> modifyRetrospectiveNote(
            @AuthenticationPrincipal CustomUser user,
            @PathVariable("noteId") Long noteId,
            @RequestBody @Valid RetrospectiveNoteModifyRequestDto requestDto
    ) {
        String username = user.getUsername();
        NoteIdResponseDto responseDto = noteService.updateRetrospectiveNote(username, noteId, requestDto);
        return ResponseEntity.ok(responseDto);
    }


}
