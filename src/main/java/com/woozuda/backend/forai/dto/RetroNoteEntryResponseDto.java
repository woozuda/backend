package com.woozuda.backend.forai.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/*
회고 관련 정보
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
/*
회고
 */
public class RetroNoteEntryResponseDto {

    private Long id; //노트 ID
    private String title; //노트 제목
    private LocalDate date; //노트 작성 날짜
    private String framework; //회고 프레임워크 (FOUR_F_S, PMI, KTP, SCS)
    private List<String> content; //노트 내용

}
