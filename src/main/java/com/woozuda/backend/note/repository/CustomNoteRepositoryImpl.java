package com.woozuda.backend.note.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.woozuda.backend.note.dto.request.NoteCondRequestDto;
import com.woozuda.backend.note.dto.response.NoteResponseDto;
import com.woozuda.backend.note.dto.response.QNoteResponseDto;
import jakarta.persistence.EntityManager;

import java.time.LocalDate;
import java.util.List;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import static com.woozuda.backend.diary.entity.QDiary.diary;
import static com.woozuda.backend.note.entity.QCommonNote.commonNote;
import static com.woozuda.backend.note.entity.QNote.note;
import static com.woozuda.backend.note.entity.QNoteContent.noteContent;
import static com.woozuda.backend.note.entity.QQuestionNote.questionNote;
import static com.woozuda.backend.note.entity.QRetrospectiveNote.retrospectiveNote;

/**
 * TODO 성능 생각하지 않음. 기능 완성한 뒤에는 성능 향상시킬 것
 */
public class CustomNoteRepositoryImpl implements CustomNoteRepository {

    private final JPAQueryFactory query;

    public CustomNoteRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(JPQLTemplates.DEFAULT, em);
    }

    @Override
    public List<NoteResponseDto> searchCommonNoteList(List<Long> idList, NoteCondRequestDto condition) {
        return query
                .from(commonNote)
                .leftJoin(commonNote.diary, diary).on(diary.id.in(idList))
                .leftJoin(noteContent).on(noteContent.note.id.eq(commonNote.id))
                .where(dateEq(condition.getDate()))
                .transform(
                        groupBy(commonNote.id).list(
                                new QNoteResponseDto(
                                        commonNote.id,
                                        diary.title,
                                        note.title,
                                        note.date.stringValue(),
                                        commonNote.weather.stringValue(),
                                        commonNote.season.stringValue(),
                                        commonNote.feeling.stringValue(),
                                        list(
                                                noteContent.content
                                        )
                                )
                        )
                );
    }

    @Override
    public List<NoteResponseDto> searchQuestionNoteList(List<Long> idList, NoteCondRequestDto condition) {
        return query
                .from(questionNote)
                .leftJoin(questionNote.diary, diary).on(diary.id.in(idList))
                .leftJoin(noteContent).on(noteContent.note.id.eq(questionNote.id))
                .where(dateEq(condition.getDate()))
                .transform(
                        groupBy(questionNote.id).list(
                                new QNoteResponseDto(
                                        questionNote.id,
                                        diary.title,
                                        note.title,
                                        note.date.stringValue(),
                                        questionNote.weather.stringValue(),
                                        questionNote.season.stringValue(),
                                        questionNote.feeling.stringValue(),
                                        questionNote.question.content,
                                        list(
                                                noteContent.content
                                        )
                                )
                        )
                );
    }

    @Override
    public List<NoteResponseDto> searchRetrospectiveNoteList(List<Long> idList, NoteCondRequestDto condition) {
        return query
                .from(retrospectiveNote)
                .leftJoin(retrospectiveNote.diary, diary).on(diary.id.in(idList))
                .leftJoin(noteContent)
                .on(noteContent.note.id.eq(retrospectiveNote.id))
                .where(dateEq(condition.getDate()))
                .orderBy(retrospectiveNote.id.asc(), noteContent.noteOrder.asc())
                .transform(
                        groupBy(retrospectiveNote.id).list(
                                new QNoteResponseDto(
                                        retrospectiveNote.id,
                                        diary.title,
                                        note.title,
                                        note.date.stringValue(),
                                        retrospectiveNote.type.stringValue(),
                                        list(
                                                noteContent.content
                                        )
                                )
                        )
                );
    }

    private static BooleanExpression dateEq(LocalDate date) {
        return date == null ? null : note.date.eq(date);
    }
}
