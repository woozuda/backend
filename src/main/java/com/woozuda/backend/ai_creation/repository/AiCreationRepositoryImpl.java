package com.woozuda.backend.ai_creation.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

public class AiCreationRepositoryImpl implements AiCreationRepositoryCustom{

    private final JPAQueryFactory query;

    public AiCreationRepositoryImpl(EntityManager entityManager) {
        this.query = new JPAQueryFactory(entityManager);
    }

}
