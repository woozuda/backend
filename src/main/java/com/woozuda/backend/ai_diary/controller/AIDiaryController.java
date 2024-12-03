package com.woozuda.backend.ai_diary.controller;

import com.woozuda.backend.ai_diary.dto.AiDiaryDTO;
import com.woozuda.backend.ai_diary.dto.DiaryDTO;
import com.woozuda.backend.ai_diary.service.AiDiaryService;
import com.woozuda.backend.ai_diary.service.DiaryAnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequiredArgsConstructor
@RequestMapping("api/report/diary")
public class AIDiaryController {
    private final DiaryAnalysisService diaryAnalysisService;
    private final AiDiaryService aiDiaryService;

    @PostMapping("/analyze")
    public ResponseEntity<AiDiaryDTO> analyzeDiary(@RequestBody DiaryDTO diaryDTO) {
        AiDiaryDTO result = diaryAnalysisService.analyzeDiary(diaryDTO);
        return ResponseEntity.ok(result);  // 분석된 결과 DTO 반환 이 부분 id 값 없음.

    }
}







