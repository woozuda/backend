package com.woozuda.backend.ai_recall.controller;

import com.woozuda.backend.account.entity.UserEntity;
import com.woozuda.backend.ai_recall.entity.Airecall_4fs;
import com.woozuda.backend.ai_recall.service.AiRecallService;
import com.woozuda.backend.ai_recall.service.AiRecll4fsAnalysisService;
import com.woozuda.backend.forai.dto.RetroNoteEntryResponseDto;
import com.woozuda.backend.forai.service.CustomeNoteRepoForAiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/report/recall")
public class AiRecallController {
    private final AiRecll4fsAnalysisService aiRecll4fsAnalysisService;
    private final AiRecallService aiRecallService;
    private final CustomeNoteRepoForAiService customeNoteRepoForAiService;

    @PostMapping("/4fs_analyzes")
    public ResponseEntity<String> anlyzerecall4fs(
            @RequestParam("start_date") LocalDate start_date,
            @RequestParam("end_date") LocalDate end_date,
            @AuthenticationPrincipal UserEntity user) {
        String username = user.getUsername();
        List<RetroNoteEntryResponseDto> recallList = customeNoteRepoForAiService.getRetroNotes(username ,start_date , end_date);
        aiRecll4fsAnalysisService.analyzeAirecall(recallList , username);
        return ResponseEntity.ok("회고 완료!");
    }
    @GetMapping("/4fs")
    public ResponseEntity<Airecall_4fs> getAiDiary(
            @RequestParam LocalDate start_date,
            @RequestParam LocalDate end_date,
            @RequestParam Long air_id) {

        return aiRecallService.getAirecall4fs(start_date, end_date, air_id)
                .map(ResponseEntity::ok) // 데이터가 있으면 200 OK
                .orElseGet(() -> ResponseEntity.noContent().build()); // 데이터가 없으면 204
    }

}
