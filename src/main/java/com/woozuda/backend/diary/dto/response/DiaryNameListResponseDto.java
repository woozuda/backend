package com.woozuda.backend.diary.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DiaryNameListResponseDto {

    private List<DiaryNameResponseDto> nameList;

    public static DiaryNameListResponseDto of(List<DiaryNameResponseDto> nameList) {
        return new DiaryNameListResponseDto(nameList);
    }

}
