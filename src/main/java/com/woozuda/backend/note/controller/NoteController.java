package com.woozuda.backend.note.controller;

import com.woozuda.backend.account.dto.CustomUser;
import com.woozuda.backend.note.dto.request.NoteCondRequestDto;
import com.woozuda.backend.note.dto.response.NoteEntryResponseDto;
import com.woozuda.backend.note.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
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

}
