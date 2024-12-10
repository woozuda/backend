package com.woozuda.backend.ai_recall.entity;

import com.woozuda.backend.account.entity.UserEntity;
import com.woozuda.backend.ai_recall.dto.Airecall_Pmi_DTO;
import com.woozuda.backend.ai_recall.dto.Airecll_Scs_DTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "retrospective_scs")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Airecall_scs extends Airecall{
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

    // 자식 엔터티 생성자: 부모 필드 + 자식 필드 초기화
    public Airecall_scs(
            UserEntity user,
            AirecallType airecallType,
            LocalDate start_date,
            LocalDate end_date,
            String start_summary,
            String start_strength,
            String start_suggestion,
            String continue_summary,
            String continue_strength,
            String continue_suggestion,
            String stop_summary,
            String stop_strength,
            String stop_suggestion,
            String start_improvement_plan,
            String continue_improvement_plan,
            String stop_improvement_plan
    ) {
        super(user,airecallType, start_date, end_date);
        this.start_summary = start_summary;
        this.start_strength = start_strength;
        this.start_suggestion = start_suggestion;
        this.continue_summary = continue_summary;
        this.continue_strength = continue_strength;
        this.continue_suggestion = continue_suggestion;
        this.stop_summary = stop_summary;
        this.stop_strength = stop_strength;
        this.stop_suggestion = stop_suggestion;
        this.start_improvement_plan = start_improvement_plan;
        this.continue_improvement_plan = continue_improvement_plan;
        this.stop_improvement_plan = stop_improvement_plan;
    }

    // 정적 팩토리 메서드
    public static Airecall_scs toairecallscsEntity(Airecll_Scs_DTO dto , UserEntity user) {
        return new Airecall_scs(
                user,
                dto.getAirecallType(),
                dto.getStart_date(),
                dto.getEnd_date(),
                dto.getStart_summary(),
                dto.getStart_strength(),
                dto.getStart_suggestion(),
                dto.getContinue_summary(),
                dto.getContinue_strength(),
                dto.getContinue_suggestion(),
                dto.getStop_summary(),
                dto.getStop_strength(),
                dto.getStop_suggestion(),
                dto.getStart_improvement_plan(),
                dto.getContinue_improvement_plan(),
                dto.getStop_improvement_plan()
        );
    }
}
