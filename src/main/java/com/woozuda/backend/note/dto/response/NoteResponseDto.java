package com.woozuda.backend.note.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class NoteResponseDto {

    private Long id; //노트 ID
    private String diary; //다이어리 제목
    private String title; //노트 제목
    private String date; //노트 작성 날짜
    private String weather; //날씨 (RETROSPECTIVE type일 때는 null)
    private String season; //계절 (RETROSPECTIVE type일 때는 null)
    private String feeling; //감정 (RETROSPECTIVE type일 때는 null)
    private String question; // 질문 (COMMON,RETROSPECTIVE type일 때는 null)
    private String framework; //회고 프레임워크 (COMMON, QUESTION type일 때는 null)
    private List<String> content; //노트 내용(type에 상관없이 배열로 저장. COMMON,QUESTION type: content.size()==1, RETROSPECTIVE type: content.size()>1)

    public static NoteResponseDto of(NoteSummaryResponseDto summaryDto, NoteDetailResponseDto detailDto) {
        return new NoteResponseDto(
                summaryDto.getNoteId(),
                summaryDto.getDiaryTitle(),
                summaryDto.getNoteTitle(),
                summaryDto.getDate().toString(),
                detailDto.getWeather(),
                detailDto.getSeason(),
                detailDto.getFeeling(),
                detailDto.getQuestion(),
                detailDto.getFramework(),
                detailDto.getContent()
        );
    }
}
