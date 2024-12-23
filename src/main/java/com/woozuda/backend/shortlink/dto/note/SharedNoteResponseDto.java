package com.woozuda.backend.shortlink.dto.note;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class SharedNoteResponseDto {
    int total;
    List<SharedNoteByDateDto> sharedNotes;
}
