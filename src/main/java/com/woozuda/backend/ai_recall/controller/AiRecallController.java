package com.woozuda.backend.ai_recall.controller;

import com.woozuda.backend.account.entity.UserEntity;
import com.woozuda.backend.ai_recall.dto.Airecall_4fs_ResponseDTO;
import com.woozuda.backend.ai_recall.entity.Airecall_4fs;
import com.woozuda.backend.ai_recall.service.AiRecallService;
import com.woozuda.backend.ai_recall.service.AiRecall_4fs_AnalysisService;
import com.woozuda.backend.forai.dto.RetroNoteEntryResponseDto;
import com.woozuda.backend.forai.service.CustomeNoteRepoForAiService;
import com.woozuda.backend.note.entity.type.Framework;
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
    private final AiRecall_4fs_AnalysisService aiRecall_4fs_AnalysisService;
    private final AiRecallService aiRecallService;
    private final CustomeNoteRepoForAiService customeNoteRepoForAiService;

    /**
     * 4FS 회고 분석 및 조회
     * @param type
     * @param start_date
     * @param end_date
     * @param user
     * @return
     */
    @PostMapping("/analyze/{type}")
    public ResponseEntity<String> analyzeRecall4fs(
            @PathVariable("type") Framework type,
            @RequestParam("start_date") LocalDate start_date,
            @RequestParam("end_date") LocalDate end_date,
            @AuthenticationPrincipal UserEntity user) {
        String username = user.getUsername();
        List<RetroNoteEntryResponseDto> recallList = customeNoteRepoForAiService.getRetroNotes(username ,start_date , end_date , type);
        aiRecall_4fs_AnalysisService.analyzeAirecall(recallList , username);
        return ResponseEntity.ok("회고 완료!");
    }

    @GetMapping("/4FS")
    public ResponseEntity<Airecall_4fs_ResponseDTO> getAiDiary(
            @RequestParam("start_date") LocalDate start_date,
            @RequestParam("end_date") LocalDate end_date,
            @RequestParam("id") Long id,
            @AuthenticationPrincipal UserEntity user) {
        Airecall_4fs_ResponseDTO responseDTO = aiRecallService.getAirecall4fs(start_date,end_date,id ,user.getUsername());

        // Airecall_4fs 서비스에서 데이터 가져오기
        return ResponseEntity.ok(responseDTO);
    }

}
