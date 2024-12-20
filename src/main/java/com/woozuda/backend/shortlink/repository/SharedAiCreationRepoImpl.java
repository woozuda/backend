package com.woozuda.backend.shortlink.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.woozuda.backend.ai_creation.entity.CreationVisibility;
import com.woozuda.backend.note.entity.Note;
import com.woozuda.backend.shortlink.dto.ai_creation.SearchSharedAiCreationDto;
import com.woozuda.backend.shortlink.dto.note.SharedNoteDto;
import jakarta.persistence.EntityManager;

import java.util.List;

import static com.woozuda.backend.account.entity.QUserEntity.userEntity;
import static com.woozuda.backend.ai_creation.entity.QAiCreation.aiCreation;
import static com.woozuda.backend.note.entity.QQuestionNote.questionNote;

public class SharedAiCreationRepoImpl implements SharedAiCreationRepo{

    private final JPAQueryFactory query;

    public SharedAiCreationRepoImpl(EntityManager em){
        this.query = new JPAQueryFactory(JPQLTemplates.DEFAULT, em);
    }

    @Override
    public List<SearchSharedAiCreationDto> searchSharedAiCreation(String username) {
        return query
                .select(Projections.fields(SearchSharedAiCreationDto.class,
                        aiCreation.ai_creation_id,
                        aiCreation.creationType,
                        aiCreation.start_date,
                        aiCreation.end_date,
                        aiCreation.image_url,
                        aiCreation.text))
                .from(aiCreation)
                .leftJoin(aiCreation.user, userEntity)
                .where(aiCreation.creationVisibility.eq(CreationVisibility.PUBLIC)
                        .and(userEntity.username.eq(username)))
                .fetch();
    }
}
