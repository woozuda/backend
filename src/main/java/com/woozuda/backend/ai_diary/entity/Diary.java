package com.woozuda.backend.ai_diary.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Diary {
    @Id
    private long id;
    private LocalDate date;
    private String title;
    private String emotion;
    private String weather;
    private String content;




}
