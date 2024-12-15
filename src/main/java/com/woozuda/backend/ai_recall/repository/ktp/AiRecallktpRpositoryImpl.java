package com.woozuda.backend.ai_recall.repository.ktp;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.woozuda.backend.account.entity.QUserEntity;
import com.woozuda.backend.ai_recall.entity.*;
import com.woozuda.backend.ai_recall.repository.fourfs.AiRecall4fsRpositoryCustom;
import jakarta.persistence.EntityManager;

import java.time.LocalDate;
import java.util.Optional;
import static com.woozuda.backend.ai_recall.entity.QAirecall_ktp.airecall_ktp;
import static com.woozuda.backend.ai_recall.entity.QAirecall.airecall;
import static com.woozuda.backend.account.entity.QUserEntity.userEntity;

public class AiRecallktpRpositoryImpl implements AiRecallktpRpositoryCustom {


    private final JPAQueryFactory query;

    public AiRecallktpRpositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public Optional<Airecall_ktp> findByAirecallktp(
            LocalDate startDate, LocalDate endDate, String username) {
        JPAQuery<Airecall_ktp> query = this.query.select(airecall_ktp)
                .from(airecall_ktp)
                .join(airecall).on(airecall_ktp.air_id.eq(airecall.air_id))
                .join(userEntity).on(airecall.user.id.eq(userEntity.id))
                .where(airecall.type.eq("KTP")
                        .and(airecall.start_date.between(startDate, endDate))
                        .and(airecall.end_date.between(startDate, endDate))
                        .and(userEntity.username.eq(username)));

        // 결과 반환
        Airecall_ktp result = query.fetchFirst();
        return Optional.ofNullable(result);
    }


}