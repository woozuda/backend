package com.woozuda.backend.mypage.controller;

import com.woozuda.backend.account.dto.CustomUser;
import com.woozuda.backend.mypage.dto.EmailDto;
import com.woozuda.backend.mypage.service.MypageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/my")
@RestController
@RequiredArgsConstructor
public class MypageController {

    private final MypageService mypageService;

    @GetMapping("/email")
    public ResponseEntity<EmailDto> getEmail(@AuthenticationPrincipal CustomUser user){
        String username = user.getUsername();
        EmailDto emailDto = mypageService.getEmail(username);
        return ResponseEntity.status(HttpStatus.OK).body(emailDto);
    }

    @PostMapping("/novel")
    public ResponseEntity<Void> makeNovel(@AuthenticationPrincipal CustomUser user){
        String username = user.getUsername();
        mypageService.makeNovel(username);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/poem")
    public ResponseEntity<Void> makePoem(@AuthenticationPrincipal CustomUser user){
        String username = user.getUsername();
        mypageService.makePoem(username);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/alarm/on")
    public ResponseEntity<Void> alarmOn(@AuthenticationPrincipal CustomUser user){
        String username = user.getUsername();
        mypageService.alarmOn(username);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/alarm/off")
    public ResponseEntity<Void> alarmOff(@AuthenticationPrincipal CustomUser user){
        String username = user.getUsername();
        mypageService.alarmOff(username);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
