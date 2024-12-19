package com.woozuda.backend.ai_recall.repository.kpt;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.woozuda.backend.ai_recall.entity.*;
import jakarta.persistence.EntityManager;

import java.time.LocalDate;
import java.util.Optional;

import static com.woozuda.backend.ai_recall.entity.QAirecall.airecall;
import static com.woozuda.backend.account.entity.QUserEntity.userEntity;
import static com.woozuda.backend.ai_recall.entity.QAirecall_kpt.airecall_kpt;

public class AiRecallkptRpositoryImpl implements AiRecallkptRpositoryCustom {


    private final JPAQueryFactory query;

    public AiRecallkptRpositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public Optional<Airecall_kpt> findByAirecallktp(
            LocalDate startDate, LocalDate endDate, String username) {
        JPAQuery<Airecall_kpt> query = this.query.select(airecall_kpt)
                .from(airecall_kpt)
                .join(airecall).on(airecall_kpt.air_id.eq(airecall.air_id))
                .join(userEntity).on(airecall.user.id.eq(userEntity.id))
                .where(airecall.type.eq("KTP")
                        .and(airecall.start_date.between(startDate, endDate))
                        .and(airecall.end_date.between(startDate, endDate))
                        .and(userEntity.username.eq(username)));

        // 결과 반환
        Airecall_kpt result = query.fetchFirst();
        return Optional.ofNullable(result);
    }


}