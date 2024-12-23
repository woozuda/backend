package com.woozuda.backend.diary.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class NoteIdResponseDto {

    private Long id;

    public static NoteIdResponseDto of(Long id) {
        return new NoteIdResponseDto(id);
    }

}
