package com.woozuda.backend.diary.entity;

import com.woozuda.backend.account.entity.UserEntity;
import com.woozuda.backend.global.entity.BaseTimeEntity;
import com.woozuda.backend.tag.entity.Tag;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//TODO 같은 사용자 내 다이어리 이름 중복 X
@Entity
@Table(name = "diary")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Diary extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diary_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", updatable = false, nullable = false)
    private UserEntity user;

    //TODO 이미지 엔티티 생성 후 해당 칼럼 수정
    @Column(name = "image_id", nullable = false)
    private String image;

    @Column(nullable = false)
    private String title;

    private LocalDate startDate;

    private LocalDate endDate;

    @Column(nullable = false)
    private Integer noteCount;

    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<DiaryTag> tagList = new ArrayList<>();

    private Diary(UserEntity user, String image, String title) {
        this.user = user;
        this.image = image;
        this.title = title;
        this.noteCount = 0;
    }

    public static Diary of(UserEntity user, String image, String title) {
        return new Diary(user, image, title);
    }

    public void addTag(Tag tag) {
        DiaryTag diaryTag = DiaryTag.of(this, tag);
        tagList.add(diaryTag);
    }

    public void change(String title, List<Tag> tags, String imgUrl) {
        this.title = title;
        this.tagList.clear();
        this.tagList.clear();
        for (Tag tag : tags) {
            addTag(tag);
        }
        this.image = imgUrl;
    }
}
