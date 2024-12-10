package com.woozuda.backend.ai_diary.repository;

import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.woozuda.backend.account.entity.QUserEntity;
import com.woozuda.backend.ai_diary.entity.AiDiary;
import com.woozuda.backend.ai_diary.entity.QAiDiary;
import jakarta.persistence.EntityManager;

import java.time.LocalDate;
import java.util.Optional;

public class AiDiaryRepositoryImpl implements AiDiaryRepositoryCustom {

    private final JPAQueryFactory query;


    public AiDiaryRepositoryImpl(EntityManager entityManager) {
        this.query = new JPAQueryFactory(entityManager);
    }

    @Override
    public Optional<AiDiary> findByAiDiary(LocalDate start_date, LocalDate end_date, Long id, String username) {
        QAiDiary aiDiary = QAiDiary.aiDiary; // QAiDiary 클래스
        QUserEntity user = QUserEntity.userEntity; // QUser 클래스 (AiDiary와 연결된 User 엔티티)

        // QueryDSL 쿼리 작성
        AiDiary result = query
                .selectFrom(aiDiary)
                .join(aiDiary.user, user)
                .where(
                        aiDiary.start_date.eq(start_date),
                        aiDiary.end_date.eq(end_date),
                        aiDiary.id.eq(id),
                        user.username.eq(username)
                )
                .fetchOne(); // 결과를 하나만 가져옵니다.

        return Optional.ofNullable(result);
    }
}
