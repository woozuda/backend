package com.woozuda.backend.ai_recall.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "retrospective_ktp")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Airecall_ktp extends Airecall{
    @Column(nullable = false, columnDefinition = "TEXT")
    private String strength_analysis;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String improvement;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String scalability;
}
