package com.woozuda.backend.note.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RetrospectiveNoteSaveRequestDto {

    @NotNull private String type;
    @NotNull private Long diaryId;
    @NotNull private String title;
    @NotNull private String date;
    @NotNull private List<String> content;

}
