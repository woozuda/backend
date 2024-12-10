package com.woozuda.backend.forai.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.woozuda.backend.forai.dto.NonRetroNoteEntryResponseDto;
import com.woozuda.backend.forai.dto.RetroNoteEntryResponseDto;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import static com.woozuda.backend.diary.entity.QDiary.diary;
import static com.woozuda.backend.note.entity.QCommonNote.commonNote;
import static com.woozuda.backend.note.entity.QNoteContent.noteContent;
import static com.woozuda.backend.note.entity.QQuestionNote.questionNote;
import static com.woozuda.backend.note.entity.QRetrospectiveNote.retrospectiveNote;

@Primary
@Repository
public class CustomNoteRepoForAiImpl implements CustomNoteRepoForAi {

    private final JPAQueryFactory query;

    public CustomNoteRepoForAiImpl(EntityManager em) {
        this.query = new JPAQueryFactory(JPQLTemplates.DEFAULT, em);
    }

    @Override
    public List<NonRetroNoteEntryResponseDto> searchNonRetroNote(String username, LocalDate startDate, LocalDate endDate) {
        List<Long> diaryIdList = getDiaryIdList(username);

        List<NonRetroNoteEntryResponseDto> commonNoteList = getCommonNoteList(startDate, endDate, diaryIdList);
        List<NonRetroNoteEntryResponseDto> questionNoteList = getQuestionNoteList(startDate, endDate, diaryIdList);

        return Stream.concat(commonNoteList.stream(), questionNoteList.stream()).collect(Collectors.toList());
    }

    private List<NonRetroNoteEntryResponseDto> getCommonNoteList(LocalDate startDate, LocalDate endDate, List<Long> diaryIdList) {
        return query
                .select(Projections.constructor(NonRetroNoteEntryResponseDto.class,
                        commonNote.dtype,
                        commonNote.id,
                        commonNote.title,
                        commonNote.date,
                        commonNote.weather.stringValue(),
                        commonNote.season.stringValue(),
                        commonNote.feeling.stringValue(),
                        noteContent.content
                ))
                .from(commonNote)
                .leftJoin(noteContent).on(noteContent.note.id.eq(commonNote.id))
                .where(commonNote.diary.id.in(diaryIdList), commonNote.date.goe(startDate), commonNote.date.loe(endDate))
                .fetch();
    }

    private List<NonRetroNoteEntryResponseDto> getQuestionNoteList(LocalDate startDate, LocalDate endDate, List<Long> diaryIdList) {
        return query
                .select(Projections.constructor(NonRetroNoteEntryResponseDto.class,
                        questionNote.dtype,
                        questionNote.id,
                        questionNote.title,
                        questionNote.date,
                        questionNote.weather.stringValue(),
                        questionNote.season.stringValue(),
                        questionNote.feeling.stringValue(),
                        questionNote.question.content,
                        noteContent.content
                ))
                .from(questionNote)
                .leftJoin(noteContent).on(noteContent.note.id.eq(questionNote.id))
                .where(questionNote.diary.id.in(diaryIdList), questionNote.date.goe(startDate), questionNote.date.loe(endDate))
                .fetch();
    }

    @Override
    public List<RetroNoteEntryResponseDto> searchRetroNote(String username, LocalDate startDate, LocalDate endDate) {
        List<Long> diaryIdList = getDiaryIdList(username);

        return query
                .from(retrospectiveNote)
                .leftJoin(noteContent).on(noteContent.note.id.eq(retrospectiveNote.id))
                .where(retrospectiveNote.diary.id.in(diaryIdList), retrospectiveNote.date.goe(startDate), retrospectiveNote.date.loe(endDate))
                .transform(
                        groupBy(retrospectiveNote.id).list(
                                Projections.constructor(RetroNoteEntryResponseDto.class,
                                        retrospectiveNote.id,
                                        retrospectiveNote.title,
                                        retrospectiveNote.date,
                                        retrospectiveNote.type.stringValue(),
                                        list(noteContent.content)
                                )
                        )
                );
    }

    private List<Long> getDiaryIdList(String username) {
        return query
                .select(diary.id)
                .from(diary)
                .where(diary.user.username.eq(username))
                .fetch();
    }
}
