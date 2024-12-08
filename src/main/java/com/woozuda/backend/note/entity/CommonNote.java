package com.woozuda.backend.note.entity;

import com.woozuda.backend.diary.entity.Diary;
import com.woozuda.backend.note.entity.type.Feeling;
import com.woozuda.backend.note.entity.type.Season;
import com.woozuda.backend.note.entity.type.Visibility;
import com.woozuda.backend.note.entity.type.Weather;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@DiscriminatorValue("COMMON")
@Table(name = "common_note")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommonNote extends Note {

    @Enumerated(EnumType.STRING)
    @Column(length = 100, nullable = false)
    private Feeling feeling;

    @Enumerated(EnumType.STRING)
    @Column(length = 100, nullable = false)
    private Weather weather;

    @Enumerated(EnumType.STRING)
    @Column(length = 100, nullable = false)
    private Season season;

    private CommonNote(Diary diary, String title, LocalDate date, Visibility visibility, Feeling feeling, Weather weather, Season season) {
        super(diary, title, date, visibility);
        this.feeling = feeling;
        this.weather = weather;
        this.season = season;
    }

    public static CommonNote of(Diary diary, String title, LocalDate date, Visibility visibility, Feeling feeling, Weather weather, Season season) {
        return new CommonNote(diary, title, date, visibility, feeling, weather, season);
    }
}
