package com.woozuda.backend.ai_recall.entity;

import com.woozuda.backend.account.entity.UserEntity;
import com.woozuda.backend.ai_recall.dto.Airecall_4fs_DTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "retrospective_4fs")
@DiscriminatorValue("4FS")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Airecall_4fs extends Airecall{
    @Column(nullable = false, columnDefinition = "TEXT")
    private String patternAnalysis;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String positiveBehavior;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String improvementSuggest;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String utilizationTips;

    // 자식 엔터티 생성자: 부모 필드 + 자식 필드 초기화
    public Airecall_4fs(
            UserEntity user,
            String airecallType,
            LocalDate start_date,
            LocalDate end_date,
            String patternAnalysis,
            String positiveBehavior,
            String improvementSuggest,
            String utilizationTips
    ) {
        super(user,airecallType, start_date, end_date);
        this.patternAnalysis = patternAnalysis;
        this.positiveBehavior = positiveBehavior;
        this.improvementSuggest = improvementSuggest;
        this.utilizationTips = utilizationTips;
    }

    // 정적 팩토리 메서드
    public static Airecall_4fs toairecall4fsEntity(Airecall_4fs_DTO dto , UserEntity user) {
        return new Airecall_4fs(
                user,
                dto.getAirecallType(),
                dto.getStart_date(),
                dto.getEnd_date(),
                dto.getPatternAnalysis(),
                dto.getPositiveBehavior(),
                dto.getImprovementSuggest(),
                dto.getUtilizationTips()
        );
    }

}
