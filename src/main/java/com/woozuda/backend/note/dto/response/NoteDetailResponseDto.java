package com.woozuda.backend.note.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

//노트 세부 정보: weather, feeling, season, question, content 등
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class NoteDetailResponseDto {

    private String weather;
    private String feeling;
    private String season;
    private String question;
    private String framework;
    private List<String> content;

}
