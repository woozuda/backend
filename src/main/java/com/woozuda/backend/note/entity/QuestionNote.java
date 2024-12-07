package com.woozuda.backend.note.entity;

import com.woozuda.backend.note.entity.type.Feeling;
import com.woozuda.backend.note.entity.type.Season;
import com.woozuda.backend.note.entity.type.Weather;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@DiscriminatorValue("QUESTION")
@Table(name = "question_note")
@Getter
public class QuestionNote extends Note {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", updatable = false, nullable = false)
    private Question question;

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
