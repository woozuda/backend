package com.woozuda.backend.note.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class NoteCountResponseDto {

    private Integer nonRetroCount;
    private Integer retroCount;

}
