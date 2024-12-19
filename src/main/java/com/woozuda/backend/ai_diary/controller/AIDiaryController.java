package com.woozuda.backend.ai_diary.controller;

import com.woozuda.backend.account.dto.CustomUser;
import com.woozuda.backend.account.entity.UserEntity;
import com.woozuda.backend.ai_creation.dto.AiCreationCountResponseDTO;
import com.woozuda.backend.ai_creation.dto.AiCreationResponseDTO;
import com.woozuda.backend.ai_diary.dto.AiDiaryCountResponseDTO;
import com.woozuda.backend.ai_diary.dto.AiDiaryResponseDTO;
import com.woozuda.backend.ai_diary.entity.AiDiary;
import com.woozuda.backend.ai_diary.service.AiDiaryService;
import com.woozuda.backend.ai_diary.service.DiaryAnalysisService;
import com.woozuda.backend.forai.dto.NonRetroNoteEntryResponseDto;
import com.woozuda.backend.forai.service.CustomeNoteRepoForAiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/report/diary")
public class AIDiaryController {
    private final DiaryAnalysisService diaryAnalysisService;
    private final AiDiaryService aiDiaryService;
    private final CustomeNoteRepoForAiService customeNoteRepoForAiService;

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
                    .body(diaryCount); // 단순히 BAD_REQUEST 반환
        }

        log.info("Diary count: {}", diaryCount);
        return ResponseEntity.ok(diaryCount); // 숫자만 반환


    }

    @PostMapping("/analyze")
    public ResponseEntity<String> analyzeDiary(
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
        // 일기 분석 실행!
        diaryAnalysisService.analyzeDiary(diaryList, username);

        // 정상적인 경우는 OK 상태와 함께 성공 메시지 또는 데이터를 반환
        return ResponseEntity.ok("일기 분석 성공");
    }

    /**
     * 사용자가 분석한 리포트를 들고오자!
     *
     * @param startDate
     * @param endDate
     * @param user
     * @return
     */
    @GetMapping
    public ResponseEntity<AiDiaryCountResponseDTO> getAiDiary(
            @RequestParam("start_date") LocalDate startDate,
            @RequestParam("end_date") LocalDate endDate,
            @AuthenticationPrincipal CustomUser user) {

        String username = user.getUsername();

        // 다이어리 카운트 가져오기
        long diaryCount = customeNoteRepoForAiService.getDiaryCount(username, startDate, endDate);

        // 다이어리 카운트가 1 이하일 경우 BAD_REQUEST 반환
        if (diaryCount <= 1) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new AiDiaryCountResponseDTO(null, diaryCount)); // AI 생성 DTO 없이 다이어리 카운트만 반환
        }

        // AI 다이어리 응답 가져오기
        AiDiaryResponseDTO responseDTO = aiDiaryService.getAiDiaryByDateRangeAndId(startDate, endDate, user.getUsername());

        // 둘을 묶어서 반환
        AiDiaryCountResponseDTO result = new AiDiaryCountResponseDTO(responseDTO, diaryCount);
        return ResponseEntity.ok(result);
    }

}








