package com.woozuda.backend.note.entity;

import com.woozuda.backend.note.entity.type.Feeling;
import com.woozuda.backend.note.entity.type.Season;
import com.woozuda.backend.note.entity.type.Weather;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@DiscriminatorValue("COMMON")
@Table(name = "common_note")
@Getter
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

}
