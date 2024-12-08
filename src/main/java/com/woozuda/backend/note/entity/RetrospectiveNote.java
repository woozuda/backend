package com.woozuda.backend.note.entity;

import com.woozuda.backend.diary.entity.Diary;
import com.woozuda.backend.note.entity.type.Framework;
import com.woozuda.backend.note.entity.type.Visibility;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.metamodel.mapping.Restrictable;

import java.time.LocalDate;

@Entity
@DiscriminatorValue("RETROSPECTIVE")
@Table(name = "retrospective_note")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RetrospectiveNote extends Note {

    @Enumerated(EnumType.STRING)
    @Column(length = 100, nullable = false)
    private Framework type;

    private RetrospectiveNote(Diary diary, String title, LocalDate date, Visibility visibility, Framework type) {
        super(diary, title, date, visibility);
        this.type = type;
    }

    public static RetrospectiveNote of(Diary diary, String title, LocalDate date, Visibility visibility, Framework type) {
        return new RetrospectiveNote(diary, title, date, visibility, type);
    }
}
