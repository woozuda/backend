package com.woozuda.backend.note.entity;

import com.woozuda.backend.diary.entity.Diary;
import com.woozuda.backend.global.entity.BaseTimeEntity;
import com.woozuda.backend.note.entity.type.Visibility;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "dtype")
@Table(name = "note")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Note extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "note_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id", nullable = false)
    private Diary diary;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(length = 100, nullable = false)
    private Visibility visibility;

    @Column(name = "dtype", insertable = false, updatable = false) // 읽기 전용 필드
    private String dtype;

    protected Note(Diary diary, String title, LocalDate date, Visibility visibility) {
        this.diary = diary;
        this.title = title;
        this.date = date;
        this.visibility = visibility;
    }
}
