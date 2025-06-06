package com.woozuda.backend.note.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CommonNoteModifyRequestDto {

    @NotNull private Long diaryId;
    @NotNull private String title;
    @NotNull private String weather;
    @NotNull private String season;
    @NotNull private String feeling;
    @NotNull private String date;
    @NotNull private String content;

}
