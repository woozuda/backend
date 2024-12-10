package com.woozuda.backend.shortlink.dto;

import com.woozuda.backend.diary.entity.Diary;
import com.woozuda.backend.note.entity.NoteContent;
import com.woozuda.backend.note.entity.Question;
import com.woozuda.backend.note.entity.type.Feeling;
import com.woozuda.backend.note.entity.type.Framework;
import com.woozuda.backend.note.entity.type.Season;
import com.woozuda.backend.note.entity.type.Weather;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class SharedRetrospectiveNoteDto extends SharedNoteDto{
    private Framework type;

    public SharedRetrospectiveNoteDto(Long id, Diary diary, String title, LocalDate date, List<String> noteContent, Framework type){
        super(id, diary, title, date, noteContent);
        this.type = type;
    }
}
