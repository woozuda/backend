package com.woozuda.backend.ai_recall.controller;

import com.woozuda.backend.account.dto.CustomUser;
import com.woozuda.backend.account.entity.UserEntity;
import com.woozuda.backend.ai_recall.dto.Airecall_4fs_ResponseDTO;
import com.woozuda.backend.ai_recall.dto.Airecall_Ktp_ResponseDTO;
import com.woozuda.backend.ai_recall.dto.Airecall_Pmi_ResponseDTO;
import com.woozuda.backend.ai_recall.dto.Airecll_Scs_ResponseDTO;
import com.woozuda.backend.ai_recall.entity.Airecall_4fs;
import com.woozuda.backend.ai_recall.service.*;
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
    private final AiRecall_4fs_AnalysisService aiRecall_4fs_AnalysisService; // 4fs
    private final AiRecall_ktp_AnalysisService aiRecall_ktp_AnalysisService; // ktp
    private final AiRecall_pmi_AnalysisService aiRecall_pmi_AnalysisService; // pmi
    private final AiRecall_scs_AnalysisService aiRecall_scs_AnalysisService; // scs
    private final AiRecallService aiRecallService;
    private final CustomeNoteRepoForAiService customeNoteRepoForAiService;

    /**
     * 4FS 회고분석
     * @param start_date
     * @param end_date
     * @param user
     */
   @PostMapping("/analyze/4FS")
    public void analyzeRecall4fs(
            @RequestParam("start_date") LocalDate start_date,
            @RequestParam("end_date") LocalDate end_date,
            @AuthenticationPrincipal CustomUser user) {

        String username = user.getUsername();
        List<RetroNoteEntryResponseDto> recallList = customeNoteRepoForAiService.getRetroNotes(username, start_date, end_date, Framework.FOUR_F_S);
        aiRecall_4fs_AnalysisService.analyzeAirecall(recallList, username);
    }
    @GetMapping("/4FS")
    public ResponseEntity<Airecall_4fs_ResponseDTO> getAiRecall4FS(
            @RequestParam("start_date") LocalDate start_date,
            @RequestParam("end_date") LocalDate end_date,
            @AuthenticationPrincipal CustomUser user) {
        Airecall_4fs_ResponseDTO responseDTO = aiRecallService.getAirecall4fs(start_date,end_date, user.getUsername());

        // Airecall_4fs 서비스에서 데이터 가져오기
        return ResponseEntity.ok(responseDTO);
    }

    /**
     * KTP 회고분석
     * @param start_date
     * @param end_date
     * @param user
     */
    @PostMapping("/analyze/KTP")
    public void analyzeRecallktp(
            @RequestParam("start_date") LocalDate start_date,
            @RequestParam("end_date") LocalDate end_date,
            @AuthenticationPrincipal CustomUser user) {

        String username = user.getUsername();
        List<RetroNoteEntryResponseDto> recallList = customeNoteRepoForAiService.getRetroNotes(username, start_date, end_date, Framework.KPT);
        aiRecall_ktp_AnalysisService.analyzeAirecall(recallList, username);
    }
    @GetMapping("/KTP")
    public ResponseEntity<Airecall_Ktp_ResponseDTO> getAiRecallKTP(
            @RequestParam("start_date") LocalDate start_date,
            @RequestParam("end_date") LocalDate end_date,
            @AuthenticationPrincipal CustomUser user) {
        Airecall_Ktp_ResponseDTO responseDTO = aiRecallService.getAirecallktp(start_date,end_date,user.getUsername());

        // Airecall_4fs 서비스에서 데이터 가져오기
        return ResponseEntity.ok(responseDTO);
    }


    /**
     * PMI 회고분석
     * @param start_date
     * @param end_date
     * @param user
     */
    @PostMapping("/analyze/PMI")
    public void analyzeRecallpmi(
            @RequestParam("start_date") LocalDate start_date,
            @RequestParam("end_date") LocalDate end_date,
            @AuthenticationPrincipal CustomUser user) {

        String username = user.getUsername();
        List<RetroNoteEntryResponseDto> recallList = customeNoteRepoForAiService.getRetroNotes(username, start_date, end_date, Framework.PMI);
        aiRecall_pmi_AnalysisService.analyzeAirecall(recallList, username);
    }
    @GetMapping("/PMI")
    public ResponseEntity<Airecall_Pmi_ResponseDTO> getAiRecallPMI(
            @RequestParam("start_date") LocalDate start_date,
            @RequestParam("end_date") LocalDate end_date,
            @AuthenticationPrincipal CustomUser user) {
        Airecall_Pmi_ResponseDTO responseDTO = aiRecallService.getAirecallpmi(start_date,end_date,user.getUsername());
        return ResponseEntity.ok(responseDTO);
    }

    /**
     * SCS 회고
     * @param start_date
     * @param end_date
     * @param user
     */
    @PostMapping("/analyze/SCS")
    public void analyzeRecallscs(
            @RequestParam("start_date") LocalDate start_date,
            @RequestParam("end_date") LocalDate end_date,
            @AuthenticationPrincipal CustomUser user) {
        String username = user.getUsername();
        List<RetroNoteEntryResponseDto> recallList = customeNoteRepoForAiService.getRetroNotes(username, start_date, end_date, Framework.SCS);
        aiRecall_scs_AnalysisService.analyzeAirecall(recallList, username);
    }
    @GetMapping("/SCS")
    public ResponseEntity<Airecll_Scs_ResponseDTO> getAiRecallSCS(
            @RequestParam("start_date") LocalDate start_date,
            @RequestParam("end_date") LocalDate end_date,
            @AuthenticationPrincipal CustomUser user) {
        Airecll_Scs_ResponseDTO responseDTO = aiRecallService.getAirecallscs(start_date,end_date,user.getUsername());
        return ResponseEntity.ok(responseDTO);
    }


}
