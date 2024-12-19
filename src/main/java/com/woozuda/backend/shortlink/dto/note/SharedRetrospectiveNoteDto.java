package com.woozuda.backend.shortlink.dto.note;

import com.woozuda.backend.note.entity.type.Framework;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class SharedRetrospectiveNoteDto extends SharedNoteDto {
    private Framework type;

    public SharedRetrospectiveNoteDto(Long id, String diary, String title, LocalDate date, List<String> noteContent, Framework type){
        super(id, diary, title, date, noteContent);
        this.type = type;
    }
}
