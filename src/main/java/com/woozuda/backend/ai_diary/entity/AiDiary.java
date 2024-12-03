package com.woozuda.backend.ai_diary.entity;


import com.woozuda.backend.ai_diary.dto.AiDiaryDTO;
import com.woozuda.backend.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "ai_diary_rep")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AiDiary extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String place;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String activity;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String emotion;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String weather;

    @Column(nullable = false)
    private float weekdayAt;

    @Column(nullable = false)
    private float weekendAt;

    @Column(nullable = false)
    private float positive;

    @Column(nullable = false)
    private float denial;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String suggestion;


    // 생성자 주입 방식
    public static AiDiary toEntity(AiDiaryDTO aiDiaryDTO){
        return new AiDiary(
                null,
                aiDiaryDTO.getPlace(),
                aiDiaryDTO.getActivity(),
                aiDiaryDTO.getEmotion(),
                aiDiaryDTO.getWeather(),
                aiDiaryDTO.getWeekdayAt(),
                aiDiaryDTO.getWeekendAt(),
                aiDiaryDTO.getPositive(),
                aiDiaryDTO.getDenial(),
                aiDiaryDTO.getSuggestion());
    }
}
