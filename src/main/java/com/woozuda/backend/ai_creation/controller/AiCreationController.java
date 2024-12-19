package com.woozuda.backend.ai_creation.controller;

import com.woozuda.backend.account.dto.CustomUser;
import com.woozuda.backend.ai_creation.dto.AiCreationResponseDTO;
import com.woozuda.backend.ai_creation.service.AiCreationService;
import com.woozuda.backend.ai_creation.service.CreationPoetryAnalysisService;
import com.woozuda.backend.ai_creation.service.CreationWritingAnalysisService;
import com.woozuda.backend.ai_diary.dto.AiDiaryResponseDTO;
import com.woozuda.backend.forai.dto.NonRetroNoteEntryResponseDto;
import com.woozuda.backend.forai.service.CustomeNoteRepoForAiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/creation")
@RestController
public class AiCreationController {
    private final AiCreationService aiCreationService;
    private final CreationPoetryAnalysisService creationPoetryAnalysisService;
    private final CreationWritingAnalysisService creationWritingAnalysisService;
    private final CustomeNoteRepoForAiService customeNoteRepoForAiService;

    @PostMapping("/analyze/poetry")
    public ResponseEntity<String> analyzeCreationPoetry(
            @RequestParam("start_date") LocalDate start_date,
            @RequestParam("end_date") LocalDate end_date,
            @AuthenticationPrincipal CustomUser user) {
        String username = user.getUsername();
        List<NonRetroNoteEntryResponseDto> diaryList = customeNoteRepoForAiService.getNonRetroNotes(username, start_date, end_date);
        long diaryCount = customeNoteRepoForAiService.getDiaryCount(username, start_date, end_date);

        if (diaryCount <= 1) {
            // 일기가 2개 미만일 경우 "ResponseEntity"로 BAD_REQUEST와 메시지를 반환
            log.info(String.valueOf(diaryCount));
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("일기가 2개 이상이어야 분석 가능합니다.");
        }

        // 시 창작 부르기
        creationPoetryAnalysisService.analyze(diaryList, username);

        // 정상적인 경우는 OK 상태와 함께 성공 메시지 또는 데이터를 반환
        return ResponseEntity.ok("일기 분석 성공");
    }
    @PostMapping("/analyze/writing")
    public ResponseEntity<String> analyzeCreationWriting(
            @RequestParam("start_date") LocalDate start_date,
            @RequestParam("end_date") LocalDate end_date,
            @AuthenticationPrincipal CustomUser user) {
        String username = user.getUsername();
        List<NonRetroNoteEntryResponseDto> diaryList = customeNoteRepoForAiService.getNonRetroNotes(username, start_date, end_date);
        long diaryCount = customeNoteRepoForAiService.getDiaryCount(username, start_date, end_date);

        if (diaryCount <= 1) {
            // 일기가 2개 미만일 경우 "ResponseEntity"로 BAD_REQUEST와 메시지를 반환
            log.info(String.valueOf(diaryCount));
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("일기가 2개 이상이어야 분석 가능합니다.");
        }

        // 글 창작 부르기
        creationWritingAnalysisService.analyze(diaryList, username);

        // 정상적인 경우는 OK 상태와 함께 성공 메시지 또는 데이터를 반환
        return ResponseEntity.ok("일기 분석 성공");
    }
    @GetMapping
    public ResponseEntity<AiCreationResponseDTO> getAiCreation(
            @RequestParam("start_date") LocalDate startDate,
            @RequestParam("end_date") LocalDate endDate,
            @AuthenticationPrincipal CustomUser user){
        AiCreationResponseDTO responseDTO = aiCreationService.getAiCreationResponseDTO(startDate, endDate, user.getUsername());
        return ResponseEntity.ok(responseDTO);
    }
    @GetMapping("/count")
    public ResponseEntity<Long> getDiaryCount(
            @RequestParam("start_date") LocalDate start_date,
            @RequestParam("end_date") LocalDate end_date,
            @AuthenticationPrincipal CustomUser user
    ) {
        String username = user.getUsername();
        long diaryCount = customeNoteRepoForAiService.getDiaryCount(username, start_date, end_date);

        if (diaryCount <= 1) {
            log.info("Diary count: {}", diaryCount);
            return ResponseEntity
                    .badRequest()
                    .body(diaryCount);
        }

        log.info("Diary count: {}", diaryCount);
        return ResponseEntity.ok(diaryCount); // 숫자만 반환


    }
}
