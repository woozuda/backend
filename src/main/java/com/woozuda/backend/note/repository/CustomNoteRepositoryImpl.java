package com.woozuda.backend.note.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.woozuda.backend.note.dto.response.NoteDetailResponseDto;
import com.woozuda.backend.note.dto.response.NoteSummaryResponseDto;
import jakarta.persistence.EntityManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.woozuda.backend.account.entity.QUserEntity.userEntity;
import static com.woozuda.backend.diary.entity.QDiary.diary;
import static com.woozuda.backend.note.entity.QCommonNote.commonNote;
import static com.woozuda.backend.note.entity.QNote.note;
import static com.woozuda.backend.note.entity.QNoteContent.noteContent;
import static com.woozuda.backend.note.entity.QQuestionNote.questionNote;
import static com.woozuda.backend.note.entity.QRetrospectiveNote.retrospectiveNote;

public class CustomNoteRepositoryImpl implements CustomNoteRepository {

    private final JPAQueryFactory query;

    public CustomNoteRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public List<NoteSummaryResponseDto> searchNoteSummary(String username, LocalDate date) {

        return query
                .select(Projections.constructor(NoteSummaryResponseDto.class,
                        note.dtype,
                        note.id,
                        diary.title,
                        note.title,
                        note.date
                ))
                .from(note)
                .join(note.diary, diary)
                .join(diary.user, userEntity)
                .where(userEntity.username.eq(username),
                        note.date.eq(date)
                )
                .fetch();
    }

    @Override
    public NoteDetailResponseDto searchCommonNoteDetail(Long noteId) {
        Tuple tuple = query
                .select(commonNote.weather, commonNote.feeling, commonNote.season, noteContent.content)
                .from(commonNote)
                .leftJoin(noteContent).on(noteContent.note.id.eq(commonNote.id))
                .where(commonNote.id.eq(noteId))
                .fetchFirst();

        if (tuple != null) {
            return new NoteDetailResponseDto(
                    tuple.get(commonNote.weather).name(),
                    tuple.get(commonNote.feeling).name(),
                    tuple.get(commonNote.season).name(),
                    null,
                    null,
                    List.of(tuple.get(noteContent.content))
            );
        } else {
            return null;
        }
    }

    @Override
    public NoteDetailResponseDto searchQuestionNoteDetail(Long noteId) {
        Tuple tuple = query
                .select(questionNote.weather, questionNote.feeling, questionNote.season, questionNote.question.content, noteContent.content)
                .from(questionNote)
                .leftJoin(noteContent).on(noteContent.note.id.eq(questionNote.id))
                .where(questionNote.id.eq(noteId))
                .fetchFirst();

        if (tuple != null) {
            return new NoteDetailResponseDto(
                    tuple.get(questionNote.weather).name(),
                    tuple.get(questionNote.feeling).name(),
                    tuple.get(questionNote.season).name(),
                    tuple.get(questionNote.question.content),
                    null,
                    List.of(tuple.get(noteContent.content))
            );
        } else {
            return null;
        }
    }

    @Override
    public NoteDetailResponseDto searchRetrospectiveNoteDetail(Long noteId) {
        List<Tuple> tuples = query
                .select(retrospectiveNote.type, noteContent.content)
                .from(retrospectiveNote)
                .leftJoin(noteContent).on(noteContent.note.id.eq(retrospectiveNote.id))
                .where(retrospectiveNote.id.eq(noteId))
                .orderBy(noteContent.noteOrder.asc())
                .fetch();

        List<String> content = new ArrayList<>();
        for (Tuple tuple : tuples) {
            content.add(tuple.get(noteContent.content));
        }

        if (!tuples.isEmpty()) {
            return new NoteDetailResponseDto(
                    null,
                    null,
                    null,
                    null,
                    tuples.getFirst().get(retrospectiveNote.type).name(),
                    content
            );
        } else {
            return null;
        }
    }
}
