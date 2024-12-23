package com.woozuda.backend.diary.dto.response;

import com.woozuda.backend.note.dto.response.NoteEntryResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DiaryDetailResponseDto {

    private Long id;
    private String title;
    private List<String> subject;
    private String imgUrl;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer noteCount;

    private Page<NoteEntryResponseDto> page;

    public static DiaryDetailResponseDto of(SingleDiaryResponseDto diarySummary, Page<NoteEntryResponseDto> page) {
        return new DiaryDetailResponseDto(
                diarySummary.getId(),
                diarySummary.getTitle(),
                diarySummary.getSubject(),
                diarySummary.getImgUrl(),
                diarySummary.getStartDate(),
                diarySummary.getEndDate(),
                diarySummary.getNoteCount(),
                page
        );
    }
}
