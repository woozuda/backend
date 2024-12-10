package com.woozuda.backend.shortlink.dto;

import com.woozuda.backend.diary.entity.Diary;
import com.woozuda.backend.note.entity.NoteContent;
import com.woozuda.backend.note.entity.Question;
import com.woozuda.backend.note.entity.type.Feeling;
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
public class SharedQuestionNoteDto extends SharedNoteDto{
    private Question question;

    private Feeling feeling;

    private Weather weather;

    private Season season;

    public SharedQuestionNoteDto(Long id, Diary diary, String title, LocalDate date, List<String> noteContent, Question question, Feeling feeling, Weather weather, Season season){
        super(id, diary, title, date, noteContent);
        this.question = question;
        this.feeling = feeling;
        this.weather = weather;
        this.season = season;
    }
}
