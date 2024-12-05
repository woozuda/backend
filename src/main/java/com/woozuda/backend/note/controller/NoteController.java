package com.woozuda.backend.note.controller;

import com.woozuda.backend.account.dto.CustomUser;
import com.woozuda.backend.note.dto.response.NoteListResponseDto;
import com.woozuda.backend.note.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/note")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @GetMapping
    public ResponseEntity<NoteListResponseDto> getNoteList(
            @AuthenticationPrincipal CustomUser user,
            @RequestParam(value = "date", required = false) String date
    ) {
        String username = user.getUsername();

        NoteListResponseDto responseDto;
        if (date == null) {
            //최신순 일기 조회
            responseDto = noteService.getNoteList(username);
        } else {
            //날짜별 일기 조회
            responseDto = noteService.getNoteList(username, LocalDate.parse(date));
        }

        return ResponseEntity.ok(responseDto);
    }

}
