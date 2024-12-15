package com.woozuda.backend.shortlink.Controller;

import com.woozuda.backend.account.dto.CustomUser;
import com.woozuda.backend.account.entity.UserEntity;
import com.woozuda.backend.account.repository.UserRepository;
import com.woozuda.backend.diary.entity.Diary;
import com.woozuda.backend.diary.repository.DiaryRepository;
import com.woozuda.backend.image.dto.ImageDto;
import com.woozuda.backend.note.entity.*;
import com.woozuda.backend.note.entity.type.*;
import com.woozuda.backend.note.repository.NoteRepository;
import com.woozuda.backend.note.repository.QuestionRepository;
import com.woozuda.backend.shortlink.Service.ShareService;
import com.woozuda.backend.shortlink.dto.NoteIdDto;
import com.woozuda.backend.shortlink.dto.SharedNoteByDateDto;
import com.woozuda.backend.shortlink.dto.SharedNoteDto;
import com.woozuda.backend.shortlink.dto.SharedNoteResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.woozuda.backend.account.entity.AiType.PICTURE_NOVEL;

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
    public ResponseEntity<SharedNoteResponseDto> getSharedNote(@AuthenticationPrincipal CustomUser customUser){
        String username = customUser.getUsername();
        SharedNoteResponseDto dtos = shareService.getSharedNote(username);
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }
}
