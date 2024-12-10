package com.woozuda.backend.ai_recall.controller;

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
     * 회고 타입에 따라 각 서비스 조회
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
        // type 에 따라 다른 분석 서비스 호출
        if (type.equals(Framework.FOUR_F_S)) {
            aiRecall_4fs_AnalysisService.analyzeAirecall(recallList, username);
        } else if (type.equals(Framework.KTP)){
            aiRecall_ktp_AnalysisService.analyzeAirecall(recallList, username);
        } else if(type.equals(Framework.PMI)){
            aiRecall_pmi_AnalysisService.analyzeAirecall(recallList, username);
        } else if (type.equals(Framework.SCS)){
            aiRecall_scs_AnalysisService.analyzeAirecall(recallList, username);
        }

        return ResponseEntity.ok("회고 완료!");
    }

    @GetMapping("/4FS")
    public ResponseEntity<Airecall_4fs_ResponseDTO> getAiRecall4FS(
            @RequestParam("start_date") LocalDate start_date,
            @RequestParam("end_date") LocalDate end_date,
            @RequestParam("id") Long id,
            @AuthenticationPrincipal UserEntity user) {
        Airecall_4fs_ResponseDTO responseDTO = aiRecallService.getAirecall4fs(start_date,end_date,id ,user.getUsername());

        // Airecall_4fs 서비스에서 데이터 가져오기
        return ResponseEntity.ok(responseDTO);
    }
    @GetMapping("/KTP")
    public ResponseEntity<Airecall_Ktp_ResponseDTO> getAiRecallKTP(
            @RequestParam("start_date") LocalDate start_date,
            @RequestParam("end_date") LocalDate end_date,
            @RequestParam("id") Long id,
            @AuthenticationPrincipal UserEntity user) {
        Airecall_Ktp_ResponseDTO responseDTO = aiRecallService.getAirecallktp(start_date,end_date,id ,user.getUsername());

        // Airecall_4fs 서비스에서 데이터 가져오기
        return ResponseEntity.ok(responseDTO);
    }
    @GetMapping("/PMI")
    public ResponseEntity<Airecall_Pmi_ResponseDTO> getAiRecallPMI(
            @RequestParam("start_date") LocalDate start_date,
            @RequestParam("end_date") LocalDate end_date,
            @RequestParam("id") Long id,
            @AuthenticationPrincipal UserEntity user) {
        Airecall_Pmi_ResponseDTO responseDTO = aiRecallService.getAirecallpmi(start_date,end_date,id ,user.getUsername());

        // Airecall_4fs 서비스에서 데이터 가져오기
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/SCS")
    public ResponseEntity<Airecll_Scs_ResponseDTO> getAiRecallSCS(
            @RequestParam("start_date") LocalDate start_date,
            @RequestParam("end_date") LocalDate end_date,
            @RequestParam("id") Long id,
            @AuthenticationPrincipal UserEntity user) {
        Airecll_Scs_ResponseDTO responseDTO = aiRecallService.getAirecallscs(start_date,end_date,id ,user.getUsername());

        // Airecall_4fs 서비스에서 데이터 가져오기
        return ResponseEntity.ok(responseDTO);
    }


}
