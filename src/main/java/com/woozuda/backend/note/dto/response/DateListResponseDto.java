package com.woozuda.backend.note.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DateListResponseDto {

    private List<DateInfoResponseDto> dateList;

    public static DateListResponseDto of(List<DateInfoResponseDto> dateList) {
        return new DateListResponseDto(dateList);
    }
}
