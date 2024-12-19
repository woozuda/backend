package com.woozuda.backend.forai.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.woozuda.backend.forai.dto.CountRecallDto;
import com.woozuda.backend.forai.dto.NonRetroNoteEntryResponseDto;
import com.woozuda.backend.forai.dto.RetroNoteEntryResponseDto;
import com.woozuda.backend.note.entity.type.Framework;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import static com.woozuda.backend.diary.entity.QDiary.diary;
import static com.woozuda.backend.note.entity.QNote.note;
import static com.woozuda.backend.note.entity.QCommonNote.commonNote;
import static com.woozuda.backend.note.entity.QNoteContent.noteContent;
import static com.woozuda.backend.note.entity.QQuestionNote.questionNote;
import static com.woozuda.backend.note.entity.QRetrospectiveNote.retrospectiveNote;

@Slf4j
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
    @Override
    public long aiDiaryCount(String username, LocalDate startDate, LocalDate endDate) {
        List<Long> diaryIdList = getDiaryIdList(username);
        List<Tuple> result = query
                .select(
                        note.dtype,
                        note.count()
                )
                .from(note)
                .where(
                        note.diary.id.in(diaryIdList),
                        note.date.between(startDate, endDate)
                )
                .groupBy(note.dtype)
                .fetch();

        int aiDiaryCount = 0;
        for (Tuple tuple : result) {
            if (tuple.get(note.dtype).equals("COMMON") || tuple.get(note.dtype).equals("QUESTION")) {
                aiDiaryCount += tuple.get(note.count());
            }
        }

        return aiDiaryCount;
    }
    @Override
    public CountRecallDto countRecallDto(String username, LocalDate startDate, LocalDate endDate) {
        // 1. 사용자의 다이어리 ID 리스트 가져오기
        List<Long> diaryIdList = getDiaryIdList(username);

        // 2. QueryDSL 쿼리 실행
        List<Tuple> result = query
        .select(
                retrospectiveNote.type,  // 'retrospectiveNote'의 'type' 컬럼
                retrospectiveNote.count(),  // 'retrospectiveNote'의 'id' 카운트
                JPAExpressions
                        .select(
                                note.dtype
                        )
                        .from(note)
                        .where(
                                note.diary.id.in(diaryIdList),
                                note.date.between(startDate, endDate),
                                note.dtype.eq("RETROSPECTIVE")
                        )
        )
                .from(retrospectiveNote)
                .groupBy(retrospectiveNote.type)  // 'type'별로 그룹화
                .fetch();

        // 3. 각 회고 타입별 카운트 초기화
        long ffs = 0;
        long ktp = 0;
        long pmi = 0;
        long scs = 0;

        // 4. 결과 처리
        for (Tuple tuple : result) {
            String typeString = tuple.get(retrospectiveNote.type).toString(); // 타입 가져오기
            long count = tuple.get(retrospectiveNote.id.count()); // 카운트 가져오기

            // 5. 타입별로 카운트 분배
            if (typeString.equals("FOUR_F_S")) {
                ffs += count;
            } else if (typeString.equals("KPT")) {
                ktp += count;
            } else if (typeString.equals("PMI")) {
                pmi += count;
            } else if (typeString.equals("SCS")) {
                scs += count;
            }
        }

        // 6. CountRecallDto 객체 반환
        return new CountRecallDto(ffs, ktp, pmi, scs);
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
    // 질문
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
    public List<RetroNoteEntryResponseDto> searchRetroNote(String username, LocalDate startDate, LocalDate endDate, Framework type) {
        List<Long> diaryIdList = getDiaryIdList(username);

        return query
                .from(retrospectiveNote)
                .leftJoin(noteContent).on(noteContent.note.id.eq(retrospectiveNote.id))
                .where(retrospectiveNote.diary.id.in(diaryIdList),
                        retrospectiveNote.date.goe(startDate),
                        retrospectiveNote.date.loe(endDate),
                        retrospectiveNote.type.eq(type)
                )
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
