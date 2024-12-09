package com.woozuda.backend.note.controller;

import com.woozuda.backend.account.dto.CustomUser;
import com.woozuda.backend.note.dto.request.CommonNoteSaveRequestDto;
import com.woozuda.backend.diary.dto.response.NoteIdResponseDto;
import com.woozuda.backend.note.dto.request.NoteCondRequestDto;
import com.woozuda.backend.note.dto.request.NoteIdRequestDto;
import com.woozuda.backend.note.dto.request.QuestionNoteSaveRequestDto;
import com.woozuda.backend.note.dto.request.RetrospectiveNoteSaveRequestDto;
import com.woozuda.backend.note.dto.response.DateListResponseDto;
import com.woozuda.backend.note.dto.response.NoteEntryResponseDto;
import com.woozuda.backend.note.service.NoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/note")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @GetMapping
    public ResponseEntity<Page<NoteEntryResponseDto>> getNoteListPage(
            @AuthenticationPrincipal CustomUser user,
            @PageableDefault Pageable pageable,
            NoteCondRequestDto condition
    ) {
        String username = user.getUsername();

        //최신순 or 날짜순 일기 조회
        Page<NoteEntryResponseDto> page = noteService.getNoteList(username, pageable, condition);

        return ResponseEntity.ok(page);
    }

    @GetMapping("/date")
    public ResponseEntity<DateListResponseDto> getNoteDates(
            @AuthenticationPrincipal CustomUser user
    ) {
        String username = user.getUsername();
        DateListResponseDto responseDto = noteService.getNoteDates(username);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteNotes(
            @AuthenticationPrincipal CustomUser user,
            @RequestBody @Valid NoteIdRequestDto requestDto
    ) {
        String username = user.getUsername();
        noteService.deleteNotes(username, requestDto);
        return ResponseEntity.ok().build();
    }

}
