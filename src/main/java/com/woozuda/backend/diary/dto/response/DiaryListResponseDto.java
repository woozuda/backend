package com.woozuda.backend.diary.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public class DiaryListResponseDto {

    private List<SingleDiaryResponseDto> diaryList;

    public DiaryListResponseDto() {
        this.diaryList = new ArrayList<>();
    }
}
