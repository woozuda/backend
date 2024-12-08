package com.woozuda.backend.ai_recall.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "retrospective_scs")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Recall_scs {
    @Id
    private Long air_id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String start_summary;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String start_strength;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String start_suggestion;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String continue_summary;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String continue_strength;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String continue_suggestion;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String stop_summary;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String stop_strength;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String stop_suggestion;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String start_improvement_plan;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String continue_improvement_plan;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String stop_improvement_plan;
}
