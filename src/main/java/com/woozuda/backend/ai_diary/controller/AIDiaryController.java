package com.woozuda.backend.ai_diary.controller;

import com.woozuda.backend.ai_diary.dto.UserDiaryDTO;
import com.woozuda.backend.ai_diary.entity.AiDiary;
import com.woozuda.backend.ai_diary.service.AiDiaryService;
import com.woozuda.backend.ai_diary.service.DiaryAnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/report/diary")
public class AIDiaryController {
    private final DiaryAnalysisService diaryAnalysisService;
    private final AiDiaryService aiDiaryService;


    @PostMapping("/analyze")
    public ResponseEntity<String> analyzeDiary(@RequestBody UserDiaryDTO userdiaryDTO) {
        diaryAnalysisService.analyzeDiary(userdiaryDTO);
        return ResponseEntity.ok("일기분석 완료!");
    }
    @GetMapping("/aidiary")
    public ResponseEntity<List<AiDiary>> getAiDiaries(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @RequestParam Long id) {

        List<AiDiary> aiDiaries = aiDiaryService.getAiDiariesByDateRangeAndId(startDate, endDate, id);
        if (aiDiaries.isEmpty()) {
            return ResponseEntity.noContent().build();  // 데이터가 없으면 204
        }
        return ResponseEntity.ok(aiDiaries);  // 데이터가 있으면 200 OK 반환
    }
}







