package com.woozuda.backend.ai_recall.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "retrospective_4fs")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Recall_4fs {
    @Id
    private Long air_id;  // 자식 테이블의 ID (Primary Key)

    @Column(nullable = false, columnDefinition = "TEXT")
    private String patternAnalysis;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String positiveBehavior;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String improvementSuggest;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String utilizationTips;




}
