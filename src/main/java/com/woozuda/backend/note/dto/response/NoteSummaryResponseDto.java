package com.woozuda.backend.note.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

//노트 요약 정보: type, note id, diary title, note title, date
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class NoteSummaryResponseDto {

    private String type;
    private Long noteId;
    private String DiaryTitle;
    private String noteTitle;
    private LocalDate date;

}
