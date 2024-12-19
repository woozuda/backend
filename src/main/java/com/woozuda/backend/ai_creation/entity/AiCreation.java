package com.woozuda.backend.ai_creation.entity;

import com.woozuda.backend.account.entity.UserEntity;
import com.woozuda.backend.ai_creation.dto.AiCreationDTO;
import com.woozuda.backend.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "ai_creation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AiCreation extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ai_creation_id;

    //유저 추가
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", updatable = false, nullable = false)
    private UserEntity user;

    @Enumerated(EnumType.STRING)
    @Column(name = "creation_type", length = 100, nullable = false)
    private CreationType creationType;

    @Column(nullable = false)
    private LocalDate start_date;

    @Column(nullable = false)
    private LocalDate end_date;

    @Column(nullable = false)
    private String image_url;

    @Column(nullable = false)
    private String text;

    @Enumerated(EnumType.STRING)
    @Column(name = "creation_visibility", length = 100, nullable = false)
    private CreationVisibility creationVisibility;

    public static AiCreation toCreationEntity(AiCreationDTO aiCreationDTO, UserEntity user) {
        CreationType creationType = CreationType.valueOf(aiCreationDTO.getCreationType());
        CreationVisibility creationVisibility = CreationVisibility.valueOf(aiCreationDTO.getCreationVisibility());
        return new AiCreation(
                null,
                user,
                creationType,  // 이미 Enum 타입이므로 그대로 사용
                aiCreationDTO.getStart_date(),
                aiCreationDTO.getEnd_date(),
                aiCreationDTO.getImageUrl(),
                aiCreationDTO.getText(),
                creationVisibility // 이미 Enum 타입이므로 그대로 사용
        );
    }
}
