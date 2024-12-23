package com.woozuda.backend.shortlink.dto.note;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SharedNoteTypeDto {

    private String type;

    private SharedNoteDto note;

}
