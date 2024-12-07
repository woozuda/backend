package com.woozuda.backend.diary.entity;

import com.woozuda.backend.global.entity.BaseTimeEntity;
import com.woozuda.backend.tag.entity.Tag;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "diary_tag")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiaryTag extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diary_tag_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id", updatable = false, nullable = false)
    private Diary diary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id", nullable = false)
    private Tag tag;

    public DiaryTag(Diary diary, Tag tag) {
        this.diary = diary;
        this.tag = tag;
    }

    public static DiaryTag of(Diary diary, Tag tag) {
        return new DiaryTag(diary, tag);
    }
}
