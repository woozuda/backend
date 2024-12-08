package com.woozuda.backend.ai_recall.entity;

import com.woozuda.backend.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "ai_recall_rep")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Airecall extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long air_id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private AirecallType airecallType;

    @Column(nullable = false)
    private LocalDate startDate;  // 시작일

    @Column(nullable = false)
    private LocalDate endDate;  // 끝일



    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "air_id")
    private Recall_4fs recall_4fs;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "air_id")
    private Recall_ktp recall_ktps;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "air_id")
    private Recall_pmi recall_pmi;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "air_id")
    private Recall_scs recall_scs;

}
