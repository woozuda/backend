package com.woozuda.backend.ai_recall.repository.pmi;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.woozuda.backend.account.entity.QUserEntity;
import com.woozuda.backend.ai_recall.entity.*;
import com.woozuda.backend.ai_recall.repository.ktp.AiRecallktpRpositoryCustom;
import jakarta.persistence.EntityManager;

import java.time.LocalDate;
import java.util.Optional;
import static com.woozuda.backend.ai_recall.entity.QAirecall_pmi.airecall_pmi;
import static com.woozuda.backend.ai_recall.entity.QAirecall.airecall;
import static com.woozuda.backend.account.entity.QUserEntity.userEntity;

public class AiRecallpmiRpositoryImpl implements AiRecallpmiRpositoryCustom {


    private final JPAQueryFactory query;

    public AiRecallpmiRpositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public Optional<Airecall_pmi> findByAirecallpmi(
            LocalDate startDate, LocalDate endDate, String username) {
        JPAQuery<Airecall_pmi> query = this.query.select(airecall_pmi)
                .from(airecall_pmi)
                .join(airecall).on(airecall_pmi.air_id.eq(airecall.air_id))
                .join(userEntity).on(airecall.user.id.eq(userEntity.id))
                .where(airecall.type.eq("PMI")
                        .and(airecall.start_date.between(startDate, endDate))
                        .and(airecall.end_date.between(startDate, endDate))
                        .and(userEntity.username.eq(username)));

        // 결과 반환
        Airecall_pmi result = query.fetchFirst();
        return Optional.ofNullable(result);
    }


}