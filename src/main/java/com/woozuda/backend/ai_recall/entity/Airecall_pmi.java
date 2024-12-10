package com.woozuda.backend.ai_recall.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "retrospective_pmi")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Airecall_pmi extends Airecall{
    @Column(nullable = false, columnDefinition = "TEXT")
    private String postive;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String minus;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String interesting;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String conclusion_action;
}
