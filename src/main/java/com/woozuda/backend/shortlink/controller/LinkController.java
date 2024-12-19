package com.woozuda.backend.shortlink.controller;


import com.woozuda.backend.account.dto.CustomUser;
import com.woozuda.backend.shortlink.dto.ai_creation.SharedAiResponse;
import com.woozuda.backend.shortlink.service.ShareService;
import com.woozuda.backend.shortlink.dto.note.SharedNoteResponseDto;
import com.woozuda.backend.shortlink.dto.ShortLinkDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/shortlink")
@RestController
@RequiredArgsConstructor
public class LinkController {

    private final ShareService shareService;

    //숏링크 해쉬 값 기반으로 공유 일기를 반환한다
    @GetMapping("/note/{hashcode}")
    public ResponseEntity<SharedNoteResponseDto> getShortlinkNoteContent(@PathVariable String hashcode){

        //hashcode -> username 도출
        String username = shareService.getUsername(hashcode);

        //username 의 사용자가 공유한 일기 리스트 반환
        SharedNoteResponseDto dtos = shareService.getSharedNote(username);

        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    //숏링크 해쉬 값 기반으로 ai 창작물을 반환 한다
    @GetMapping("/ai/{hashcode}")
    public ResponseEntity<SharedAiResponse> getShortlinkAiContent(@PathVariable String hashcode){

        //hashcode -> username 도출
        String username = shareService.getUsername(hashcode);

        //username 의 사용자가 공유한 ai 창작물
        SharedAiResponse dtos = shareService.getSharedAiCreation(username);

        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    //특정 유저의 숏링크 값을 받는다
    @GetMapping("")
    public ResponseEntity<ShortLinkDto> getShortLink(@AuthenticationPrincipal CustomUser customUser) {

        String username = customUser.getUsername();

        ShortLinkDto shortlinkDto = shareService.getShortLink(username);

        return ResponseEntity.status(HttpStatus.OK).body(shortlinkDto);

    }


}
