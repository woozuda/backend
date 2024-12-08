package com.woozuda.backend.note.controller;

import com.woozuda.backend.account.dto.CustomUser;
import com.woozuda.backend.diary.dto.response.NoteIdResponseDto;
import com.woozuda.backend.note.dto.request.CommonNoteModifyRequestDto;
import com.woozuda.backend.note.dto.request.CommonNoteSaveRequestDto;
import com.woozuda.backend.note.dto.response.NoteResponseDto;
import com.woozuda.backend.note.service.CommonNoteService;
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
@RequestMapping("/api/note/common")
@RequiredArgsConstructor
public class CommonNoteController {

    private final CommonNoteService noteService;

    @PostMapping
    public ResponseEntity<NoteIdResponseDto> createCommonNote(
            @AuthenticationPrincipal CustomUser user,
            @RequestBody @Valid CommonNoteSaveRequestDto requestDto
    ) {
        String username = user.getUsername();
        NoteIdResponseDto responseDto = noteService.saveCommonNote(username, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{noteId}")
    public ResponseEntity<NoteResponseDto> getCommonNote(
            @AuthenticationPrincipal CustomUser user,
            @PathVariable("noteId") Long noteId
    ) {
        String username = user.getUsername();
        NoteResponseDto responseDto = noteService.getCommonNote(username, noteId);
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/{noteId}")
    public ResponseEntity<NoteIdResponseDto> modifyCommonNote(
            @AuthenticationPrincipal CustomUser user,
            @PathVariable("noteId") Long noteId,
            @RequestBody @Valid CommonNoteModifyRequestDto requestDto
    ) {
        String username = user.getUsername();
        NoteIdResponseDto responseDto = noteService.updateCommonNote(username, noteId, requestDto);
        return ResponseEntity.ok(responseDto);
    }


}
