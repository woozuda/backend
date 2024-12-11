package com.woozuda.backend.ai_recall.entity;

import com.woozuda.backend.account.entity.UserEntity;
import com.woozuda.backend.ai_recall.dto.Airecall_Ktp_DTO;
import com.woozuda.backend.ai_recall.dto.Airecall_Pmi_DTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "retrospective_pmi")
@Getter
@DiscriminatorValue("PMI")
@NoArgsConstructor
@AllArgsConstructor
public class Airecall_pmi extends Airecall{
    @Column(nullable = false, columnDefinition = "TEXT")
    private String positive;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String minus;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String interesting;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String conclusion_action;

    // 자식 엔터티 생성자: 부모 필드 + 자식 필드 초기화
    public Airecall_pmi(
            UserEntity user,
            String type,
            LocalDate start_date,
            LocalDate end_date,
            String positive,
            String minus,
            String interesting,
            String conclusion_action
    ) {
        super(user,type, start_date, end_date);
        this.positive = positive;
        this.minus = minus;
        this.interesting = interesting;
        this.conclusion_action = conclusion_action;
    }

    // 정적 팩토리 메서드
    public static Airecall_pmi toairecallpmiEntity(Airecall_Pmi_DTO dto , UserEntity user) {
        return new Airecall_pmi(
                user,
                dto.getAirecallType(),
                dto.getStart_date(),
                dto.getEnd_date(),
                dto.getPositive(),
                dto.getMinus(),
                dto.getInteresting(),
                dto.getConclusion_action()
        );
    }
}
