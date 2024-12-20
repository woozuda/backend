package com.woozuda.backend.ai_recall.controller;

import com.woozuda.backend.account.dto.CustomUser;
import com.woozuda.backend.ai_recall.dto.Airecall_4fs_ResponseDTO;
import com.woozuda.backend.ai_recall.dto.Airecall_Kpt_ResponseDTO;
import com.woozuda.backend.ai_recall.dto.Airecall_Pmi_ResponseDTO;
import com.woozuda.backend.ai_recall.dto.Airecll_Scs_ResponseDTO;
import com.woozuda.backend.ai_recall.service.*;
import com.woozuda.backend.forai.dto.RetroNoteEntryResponseDto;
import com.woozuda.backend.forai.service.CustomeNoteRepoForAiService;
import com.woozuda.backend.note.entity.type.Framework;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/report/recall")
public class AiRecallController {
    private final AiRecall_4fs_AnalysisService aiRecall_4fs_AnalysisService; // 4fs
    private final AiRecall_kpt_AnalysisService aiRecall_kpt_AnalysisService; // ktp
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
    public ResponseEntity<String> analyzeRecall4fs(
            @RequestParam("start_date") LocalDate start_date,
            @RequestParam("end_date") LocalDate end_date,
            @AuthenticationPrincipal CustomUser user) {

       String username = user.getUsername();
       long result = customeNoteRepoForAiService.getCount4fs(username, start_date, end_date, Framework.FOUR_F_S);
       if(result <= 1) {
           return ResponseEntity
                   .status(HttpStatus.BAD_REQUEST)
                   .body("회고가 2개 이상이어야 분석 가능합니다.");
       }
        List<RetroNoteEntryResponseDto> recallList = customeNoteRepoForAiService.getRetroNotes(username, start_date, end_date, Framework.FOUR_F_S);
        aiRecall_4fs_AnalysisService.analyzeAirecall(recallList, username);
        return ResponseEntity.ok("성공");
    }

    @GetMapping("/4FS")
    public ResponseEntity<Airecall_4fs_ResponseDTO> getAiRecall4FS(
            @RequestParam("start_date") LocalDate start_date,
            @RequestParam("end_date") LocalDate end_date,
            @AuthenticationPrincipal CustomUser user) {
        Airecall_4fs_ResponseDTO responseDTO = aiRecallService.getAirecall4fs(start_date,end_date, user.getUsername());
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/4FS/count")
    public ResponseEntity<Long> getAiRecall4FSCount(
            @RequestParam("start_date") LocalDate start_date,
            @RequestParam("end_date") LocalDate end_date,
            @AuthenticationPrincipal CustomUser user) {
        String username = user.getUsername();
        long result = customeNoteRepoForAiService.getCount4fs(username, start_date, end_date, Framework.FOUR_F_S);
        if(result <= 1) {
            return ResponseEntity
                    .badRequest()
                    .body(result);
        }
        return ResponseEntity.ok(result);
    }

    /**
     * KTP 회고분석
     * @param start_date
     * @param end_date
     * @param user
     */
    @PostMapping("/analyze/KPT")
    public ResponseEntity<String> analyzeRecallktp(
            @RequestParam("start_date") LocalDate start_date,
            @RequestParam("end_date") LocalDate end_date,
            @AuthenticationPrincipal CustomUser user) {

        String username = user.getUsername();
        long result = customeNoteRepoForAiService.getCount4fs(username, start_date, end_date, Framework.KPT);
        if(result <= 1) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("회고가 2개 이상이어야 분석 가능합니다.");
        }
        List<RetroNoteEntryResponseDto> recallList = customeNoteRepoForAiService.getRetroNotes(username, start_date, end_date, Framework.KPT);
        aiRecall_kpt_AnalysisService.analyzeAirecall(recallList, username);
        return ResponseEntity.ok("KPT 분석 완료");
    }

    @GetMapping("/KPT")
    public ResponseEntity<Airecall_Kpt_ResponseDTO> getAiRecallKTP(
            @RequestParam("start_date") LocalDate start_date,
            @RequestParam("end_date") LocalDate end_date,
            @AuthenticationPrincipal CustomUser user) {
        Airecall_Kpt_ResponseDTO responseDTO = aiRecallService.getAirecallktp(start_date,end_date,user.getUsername());
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/KPT/count")
    public ResponseEntity<Long> getAiRecallKPTCount(
            @RequestParam("start_date") LocalDate start_date,
            @RequestParam("end_date") LocalDate end_date,
            @AuthenticationPrincipal CustomUser user) {
        String username = user.getUsername();
        long result = customeNoteRepoForAiService.getCountkpt(username, start_date, end_date, Framework.KPT);
        if(result <= 1) {
            return ResponseEntity
                    .badRequest()
                    .body(result);
        }
        return ResponseEntity.ok(result);
    }


    /**
     * PMI 회고분석
     * @param start_date
     * @param end_date
     * @param user
     */
    @PostMapping("/analyze/PMI")
    public ResponseEntity<String> analyzeRecallpmi(
            @RequestParam("start_date") LocalDate start_date,
            @RequestParam("end_date") LocalDate end_date,
            @AuthenticationPrincipal CustomUser user) {

        String username = user.getUsername();
        long result = customeNoteRepoForAiService.getCount4fs(username, start_date, end_date, Framework.PMI);
        if(result <= 1) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("회고가 2개 이상이어야 분석 가능합니다.");
        }
        List<RetroNoteEntryResponseDto> recallList = customeNoteRepoForAiService.getRetroNotes(username, start_date, end_date, Framework.PMI);
        aiRecall_pmi_AnalysisService.analyzeAirecall(recallList, username);
        return ResponseEntity.ok("PMI 성공");
    }

    @GetMapping("/PMI")
    public ResponseEntity<Airecall_Pmi_ResponseDTO> getAiRecallPMI(
            @RequestParam("start_date") LocalDate start_date,
            @RequestParam("end_date") LocalDate end_date,
            @AuthenticationPrincipal CustomUser user) {
        Airecall_Pmi_ResponseDTO responseDTO = aiRecallService.getAirecallpmi(start_date,end_date,user.getUsername());
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/PMI/count")
    public ResponseEntity<Long> getAiRecallPMICount(
            @RequestParam("start_date") LocalDate start_date,
            @RequestParam("end_date") LocalDate end_date,
            @AuthenticationPrincipal CustomUser user) {
        String username = user.getUsername();
        long result = customeNoteRepoForAiService.getCountpmi(username, start_date, end_date, Framework.PMI);
        if(result <= 1) {
            return ResponseEntity
                    .badRequest()
                    .body(result);
        }
        return ResponseEntity.ok(result);
    }

    /**
     * SCS 회고
     * @param start_date
     * @param end_date
     * @param user
     */
    @PostMapping("/analyze/SCS")
    public ResponseEntity<String> analyzeRecallscs(
            @RequestParam("start_date") LocalDate start_date,
            @RequestParam("end_date") LocalDate end_date,
            @AuthenticationPrincipal CustomUser user) {
        String username = user.getUsername();
        long result = customeNoteRepoForAiService.getCount4fs(username, start_date, end_date, Framework.SCS);
        if(result <= 1) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("회고가 2개 이상이어야 분석 가능합니다.");
        }
        List<RetroNoteEntryResponseDto> recallList = customeNoteRepoForAiService.getRetroNotes(username, start_date, end_date, Framework.SCS);
        aiRecall_scs_AnalysisService.analyzeAirecall(recallList, username);
        return ResponseEntity.ok("SCS 성공");
    }
    @GetMapping("/SCS")
    public ResponseEntity<Airecll_Scs_ResponseDTO> getAiRecallSCS(
            @RequestParam("start_date") LocalDate start_date,
            @RequestParam("end_date") LocalDate end_date,
            @AuthenticationPrincipal CustomUser user) {
        Airecll_Scs_ResponseDTO responseDTO = aiRecallService.getAirecallscs(start_date,end_date,user.getUsername());
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/SCS/count")
    public ResponseEntity<Long> getAiRecallSCSCount(
            @RequestParam("start_date") LocalDate start_date,
            @RequestParam("end_date") LocalDate end_date,
            @AuthenticationPrincipal CustomUser user) {
        String username = user.getUsername();
        long result = customeNoteRepoForAiService.getCountscs(username, start_date, end_date, Framework.SCS);
        if(result <= 1) {
            return ResponseEntity
                    .badRequest()
                    .body(result);
        }
        return ResponseEntity.ok(result);
    }

}
