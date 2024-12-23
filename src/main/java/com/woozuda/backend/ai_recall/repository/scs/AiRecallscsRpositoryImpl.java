package com.woozuda.backend.ai_recall.repository.scs;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.woozuda.backend.account.entity.QUserEntity;
import com.woozuda.backend.ai_recall.entity.*;
import com.woozuda.backend.ai_recall.repository.pmi.AiRecallpmiRpositoryCustom;
import jakarta.persistence.EntityManager;

import java.time.LocalDate;
import java.util.Optional;
import static com.woozuda.backend.ai_recall.entity.QAirecall_scs.airecall_scs;
import static com.woozuda.backend.ai_recall.entity.QAirecall.airecall;
import static com.woozuda.backend.account.entity.QUserEntity.userEntity;

public class AiRecallscsRpositoryImpl implements AiRecallscsRpositoryCustom {


    private final JPAQueryFactory query;

    public AiRecallscsRpositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public Optional<Airecall_scs> findByAirecallscs(
            LocalDate startDate, LocalDate endDate, String username) {
        JPAQuery<Airecall_scs> query = this.query.select(airecall_scs)
                .from(airecall_scs)
                .join(airecall).on(airecall_scs.air_id.eq(airecall.air_id))
                .join(userEntity).on(airecall.user.id.eq(userEntity.id))
                .where(airecall.type.eq("PMI")
                        .and(airecall.start_date.between(startDate, endDate))
                        .and(airecall.end_date.between(startDate, endDate))
                        .and(userEntity.username.eq(username)));

        // 결과 반환
        Airecall_scs result = query.fetchFirst();
        return Optional.ofNullable(result);
    }


}