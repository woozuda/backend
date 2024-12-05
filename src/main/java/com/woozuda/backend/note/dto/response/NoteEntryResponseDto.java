package com.woozuda.backend.note.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class NoteEntryResponseDto implements Comparable<NoteEntryResponseDto>{

    private String type; //노트 종류 (COMMON, QUESTION, RETROSPECTIVE)
    private NoteResponseDto note;

    public static NoteEntryResponseDto of(NoteSummaryResponseDto summaryDto, NoteDetailResponseDto detailDto) {
        return new NoteEntryResponseDto(summaryDto.getType(), NoteResponseDto.of(summaryDto, detailDto));
    }

    @Override
    public int compareTo(NoteEntryResponseDto o) {
        if (this.type == null && o.type == null) {
            return 0;
        }
        if (this.type == null) {
            return 1;
        }
        if (o.type == null) {
            return -1;
        }
        // 사전순의 반대 (내림차순 정렬)
        return o.getNote().getDate().compareTo(this.getNote().getDate());
    }
}
