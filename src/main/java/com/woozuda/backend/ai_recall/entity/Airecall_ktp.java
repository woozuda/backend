package com.woozuda.backend.ai_recall.entity;

import com.woozuda.backend.account.entity.UserEntity;
import com.woozuda.backend.ai_recall.dto.Airecall_Ktp_DTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "retrospective_ktp")
@DiscriminatorValue("KTP")
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

    // 자식 엔터티 생성자: 부모 필드 + 자식 필드 초기화
    public Airecall_ktp(
            UserEntity user,
            String type,
            LocalDate start_date,
            LocalDate end_date,
            String strength_analysis,
            String improvement,
            String scalability
    ) {
        super(user,type, start_date, end_date);
        this.strength_analysis = strength_analysis;
        this.improvement = improvement;
        this.scalability = scalability;
    }

    // 정적 팩토리 메서드
    public static Airecall_ktp toairecallktpEntity(Airecall_Ktp_DTO dto , UserEntity user) {
        return new Airecall_ktp(
                user,
                dto.getAirecallType(),
                dto.getStart_date(),
                dto.getEnd_date(),
                dto.getStrength_analysis(),
                dto.getImprovement(),
                dto.getScalability()
        );
    }
}
