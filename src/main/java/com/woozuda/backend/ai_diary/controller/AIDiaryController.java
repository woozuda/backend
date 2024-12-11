package com.woozuda.backend.ai_diary.controller;

import com.woozuda.backend.account.entity.UserEntity;
import com.woozuda.backend.ai_diary.dto.AiDiaryResponseDTO;
import com.woozuda.backend.ai_diary.entity.AiDiary;
import com.woozuda.backend.ai_diary.service.AiDiaryService;
import com.woozuda.backend.ai_diary.service.DiaryAnalysisService;
import com.woozuda.backend.forai.dto.NonRetroNoteEntryResponseDto;
import com.woozuda.backend.forai.service.CustomeNoteRepoForAiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/report/diary")
public class AIDiaryController {
    private final DiaryAnalysisService diaryAnalysisService;
    private final AiDiaryService aiDiaryService;
    private final CustomeNoteRepoForAiService customeNoteRepoForAiService;

    @PostMapping("/analyze")
    public void analyzeDiary(
            @RequestParam("start_date") LocalDate start_date,
            @RequestParam("end_date") LocalDate end_date,
            @AuthenticationPrincipal UserEntity user) {
        // 유저 아이디 찾고!
        String username = user.getUsername();

        // start_date와 end_date , 유저 아이디 전달하고!
        List<NonRetroNoteEntryResponseDto> diaryList = customeNoteRepoForAiService.getNonRetroNotes(username ,start_date , end_date);

        // 일기 분석 실행!
        diaryAnalysisService.analyzeDiary(diaryList, username);
    }

    /**
     * 사용자가 분석한 리포트를 들고오자!
     * @param startDate
     * @param endDate
     * @param id
     * @param user
     * @return
     */
    @GetMapping
    public ResponseEntity<AiDiaryResponseDTO> getAiDiary(
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate,
            @RequestParam("id") Long id,
            @AuthenticationPrincipal UserEntity user) {
        AiDiaryResponseDTO responseDTO = aiDiaryService.getAiDiaryByDateRangeAndId(startDate, endDate, id, user.getUsername());
            return ResponseEntity.ok(responseDTO);
        }
    }








