package com.woozuda.backend.diary.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DiarySaveRequestDto {

    @NotNull
    private String title;

    @NotNull
    private List<String> tags;

    private String imgUrl;

}
