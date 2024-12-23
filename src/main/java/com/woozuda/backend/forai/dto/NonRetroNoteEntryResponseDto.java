package com.woozuda.backend.forai.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
/**
 * 일기
 */
public class NonRetroNoteEntryResponseDto {

    private String type; //일기 종류(COMMON, QUESTION)
    private Long id; //노트 ID
    private String title; //노트 제목
    private LocalDate date; //노트 작성 날짜
    private String weather; //날씨 (RETROSPECTIVE type일 때는 null)
    private String season; //계절 (RETROSPECTIVE type일 때는 null)
    private String feeling; //감정 (RETROSPECTIVE type일 때는 null)
    private String question; // 질문 (COMMON,RETROSPECTIVE type일 때는 null)
    private String content; //노트 내용e

    public NonRetroNoteEntryResponseDto(String type, Long id, String title, LocalDate date, String weather, String season, String feeling, String content) {
        this.type = type;
        this.id = id;
        this.title = title;
        this.date = date;
        this.weather = weather;
        this.season = season;
        this.feeling = feeling;
        this.content = content;
    }
}
