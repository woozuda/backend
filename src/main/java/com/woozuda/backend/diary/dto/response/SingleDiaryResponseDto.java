package com.woozuda.backend.diary.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SingleDiaryResponseDto {

    private Long id;
    private String title;
    private List<String> tags;
    private String imgUrl;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer noteCount;

    public static SingleDiaryResponseDto of(
            Long id,
            String title,
            List<String> subject,
            String imgUrl,
            LocalDate startDate,
            LocalDate endDate,
            Integer noteCount
    ) {
        return new SingleDiaryResponseDto(id, title, subject, imgUrl, startDate, endDate, noteCount);
    }
}
