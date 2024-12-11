package com.woozuda.backend.ai_diary.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DiaryEntity {
    @Id
    private String id;
    private String title;
    private String date;
    private String emotion;
    private String weather;
    private String content;
}
