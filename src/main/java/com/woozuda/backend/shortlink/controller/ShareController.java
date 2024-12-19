package com.woozuda.backend.shortlink.controller;

import com.woozuda.backend.account.dto.CustomUser;
import com.woozuda.backend.account.entity.UserEntity;
import com.woozuda.backend.account.repository.UserRepository;
import com.woozuda.backend.ai_creation.entity.AiCreation;
import com.woozuda.backend.ai_creation.entity.CreationType;
import com.woozuda.backend.ai_creation.entity.CreationVisibility;
import com.woozuda.backend.ai_creation.repository.AiCreationRepository;
import com.woozuda.backend.shortlink.dto.ai_creation.AiCreationIdDto;
import com.woozuda.backend.shortlink.dto.ai_creation.SharedAiResponse;
import com.woozuda.backend.shortlink.service.ShareService;
import com.woozuda.backend.shortlink.dto.note.NoteIdDto;
import com.woozuda.backend.shortlink.dto.note.SharedNoteResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;

@RequestMapping("/api/shared")
@RestController
@RequiredArgsConstructor
public class ShareController {

    private final ShareService shareService;

    @PostMapping("/note")
    public ResponseEntity<Void> makeSharedNote(@RequestBody NoteIdDto noteIdDto) {
        shareService.makeSharedNote(noteIdDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/note/unshare")
    public ResponseEntity<Void> makeUnsharedNote(@RequestBody NoteIdDto noteIdDto) {
        shareService.makeUnsharedNote(noteIdDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/ai")
    public ResponseEntity<Void> makeSharedAiCreation(@RequestBody AiCreationIdDto aiCreationIdDto){
        shareService.makeSharedAiCreation(aiCreationIdDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/ai/unshare")
    public ResponseEntity<Void> makeUnsharedAiCreation(@RequestBody AiCreationIdDto aiCreationIdDto){
        shareService.makeUnsharedAiCreation(aiCreationIdDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @GetMapping("/note")
    public ResponseEntity<SharedNoteResponseDto> getSharedNote(@AuthenticationPrincipal CustomUser customUser){
        String username = customUser.getUsername();
        SharedNoteResponseDto dtos = shareService.getSharedNote(username);
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    @GetMapping("/ai")
    public ResponseEntity<SharedAiResponse> getSharedAiCreation(@AuthenticationPrincipal CustomUser customUser){
        String username = customUser.getUsername();
        SharedAiResponse dtos = shareService.getSharedAiCreation(username);
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }
}
