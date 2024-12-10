package com.woozuda.backend.shortlink.Controller;

import com.woozuda.backend.account.dto.CustomUser;
import com.woozuda.backend.account.entity.UserEntity;
import com.woozuda.backend.image.dto.ImageDto;
import com.woozuda.backend.shortlink.Service.ShareService;
import com.woozuda.backend.shortlink.dto.NoteIdDto;
import com.woozuda.backend.shortlink.dto.SharedNoteDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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

    @GetMapping("/note")
    public ResponseEntity<List<SharedNoteDto>> getSharedNote(@AuthenticationPrincipal CustomUser customUser){

        String username = customUser.getUsername();
        List<SharedNoteDto> dtos = shareService.getSharedNote(username);
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }
}
