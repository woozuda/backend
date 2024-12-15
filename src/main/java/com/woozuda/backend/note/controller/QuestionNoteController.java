package com.woozuda.backend.note.controller;

import com.woozuda.backend.account.dto.CustomUser;
import com.woozuda.backend.diary.dto.response.NoteIdResponseDto;
import com.woozuda.backend.note.dto.request.CommonNoteSaveRequestDto;
import com.woozuda.backend.note.dto.request.QuestionNoteModifyRequestDto;
import com.woozuda.backend.note.dto.request.QuestionNoteSaveRequestDto;
import com.woozuda.backend.note.dto.response.NoteResponseDto;
import com.woozuda.backend.note.service.QuestionNoteService;
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
@RequestMapping("/api/note/question")
@RequiredArgsConstructor
public class QuestionNoteController {

    private final QuestionNoteService noteService;

    @PostMapping
    public ResponseEntity<NoteIdResponseDto> createQuestionNote(
            @AuthenticationPrincipal CustomUser user,
            @RequestBody @Valid QuestionNoteSaveRequestDto requestDto
    ) {
        String username = user.getUsername();
        NoteIdResponseDto responseDto = noteService.saveQuestionNote(username, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{noteId}")
    public ResponseEntity<NoteResponseDto> getQuestionNote(
            @AuthenticationPrincipal CustomUser user,
            @PathVariable("noteId") Long noteId
    ) {
        String username = user.getUsername();
        NoteResponseDto responseDto = noteService.getQuestionNote(username, noteId);
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/{noteId}")
    public ResponseEntity<NoteIdResponseDto> modifyQuestionNote(
            @AuthenticationPrincipal CustomUser user,
            @PathVariable("noteId") Long noteId,
            @RequestBody @Valid QuestionNoteModifyRequestDto requestDto
    ) {
        String username = user.getUsername();
        NoteIdResponseDto responseDto = noteService.updateQuestionNote(username, noteId, requestDto);
        return ResponseEntity.ok(responseDto);
    }

}
