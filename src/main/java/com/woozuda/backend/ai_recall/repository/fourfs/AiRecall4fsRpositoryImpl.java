package com.woozuda.backend.ai_recall.repository.fourfs;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.woozuda.backend.account.entity.QUserEntity;
import com.woozuda.backend.ai_recall.entity.Airecall_4fs;
import com.woozuda.backend.ai_recall.entity.QAirecall;
import com.woozuda.backend.ai_recall.entity.QAirecall_4fs;
import jakarta.persistence.EntityManager;

import java.time.LocalDate;
import java.util.Optional;


import static com.woozuda.backend.ai_recall.entity.QAirecall_4fs.airecall_4fs;
import static com.woozuda.backend.ai_recall.entity.QAirecall.airecall;
import static com.woozuda.backend.account.entity.QUserEntity.userEntity;

public class AiRecall4fsRpositoryImpl implements AiRecall4fsRpositoryCustom {


    private final JPAQueryFactory query;

    public AiRecall4fsRpositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }
    @Override
    public Optional<Airecall_4fs> findByAirecall4FS(
            LocalDate startDate, LocalDate endDate, Long airId, String username) {

        JPAQuery<Airecall_4fs> query = this.query.select(airecall_4fs)
                .from(airecall_4fs)
                .join(airecall).on(airecall_4fs.air_id.eq(airecall.air_id))
                .join(userEntity).on(airecall.user.id.eq(userEntity.id))
                .where(airecall.type.eq("4FS")
                        .and(airecall.start_date.between(startDate, endDate))
                        .and(airecall.end_date.between(startDate, endDate))
                        .and(airecall.air_id.eq(airId))
                        .and(userEntity.username.eq(username)));

        // 결과 반환
        Airecall_4fs result = query.fetchFirst();
        return Optional.ofNullable(result);
    }
}