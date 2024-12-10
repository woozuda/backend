package com.woozuda.backend.ai_recall.entity;

import com.woozuda.backend.account.entity.UserEntity;
import com.woozuda.backend.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "ai_recall_rep")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Airecall extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long air_id;

    //유저 추가
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", updatable = false, nullable = false)
    private UserEntity user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AirecallType airecallType;

    @Column(nullable = false)
    private LocalDate start_date;  // 시작일

    @Column(nullable = false)
    private LocalDate end_date;  // 끝일

    public Airecall(UserEntity username, AirecallType airecallType, LocalDate start_date, LocalDate end_date) {
        this.user = username;
        this.airecallType = airecallType;
        this.start_date = start_date;
        this.end_date = end_date;

    }


}
