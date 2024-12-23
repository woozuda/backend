package com.woozuda.backend.shortlink.dto.note;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class SharedNoteByDateDto {
    LocalDate date;
    List<SharedNoteTypeDto> notes;
}
