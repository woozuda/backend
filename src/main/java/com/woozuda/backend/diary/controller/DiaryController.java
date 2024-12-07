package com.woozuda.backend.diary.controller;

import com.woozuda.backend.account.dto.CustomUser;
import com.woozuda.backend.diary.dto.request.DiarySaveRequestDto;
import com.woozuda.backend.diary.dto.response.DiaryIdResponseDto;
import com.woozuda.backend.diary.dto.response.DiaryListResponseDto;
import com.woozuda.backend.diary.service.DiaryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/diary")
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;


    @GetMapping
    public ResponseEntity<DiaryListResponseDto> getDiaryList(
            @AuthenticationPrincipal CustomUser user //TODO 8번 pr 머지 후 CustomUserDetails 클래스명 바뀜
    ) {
        String username = user.getUsername();
        DiaryListResponseDto responseDto = diaryService.getDairyList(username);
        return ResponseEntity.ok(responseDto);
    }


    @PostMapping
    public ResponseEntity<DiaryIdResponseDto> createDiary (
            @AuthenticationPrincipal CustomUser user, //TODO 8번 pr 머지 후 CustomUserDetails 클래스명 바뀜
            @RequestBody @Valid DiarySaveRequestDto requestDto
    ) {
        String username = user.getUsername();
        DiaryIdResponseDto responseDto = diaryService.saveDiary(username, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/{diaryId}")
    public ResponseEntity<DiaryIdResponseDto> modifyDiary(
            @AuthenticationPrincipal CustomUser user, //TODO 8번 pr 머지 후 CustomUserDetails 클래스명 바뀜
            @PathVariable("diaryId") Long diaryId,
            @RequestBody @Valid DiarySaveRequestDto requestDto
    ) {
        String username = user.getUsername();
        DiaryIdResponseDto responseDto = diaryService.updateDiary(username, diaryId, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{diaryId}")
    public ResponseEntity<Void> deleteDiary(
            @AuthenticationPrincipal CustomUser user, //TODO 8번 pr 머지 후 CustomUserDetails 클래스명 바뀜
            @PathVariable("diaryId") Long diaryId
    ) {
        String username = user.getUsername();
        diaryService.removeDiary(username, diaryId);
        return ResponseEntity.ok().build();
    }

}
