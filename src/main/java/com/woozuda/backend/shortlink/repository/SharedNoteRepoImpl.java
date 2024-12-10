package com.woozuda.backend.shortlink.repository;

import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.woozuda.backend.note.entity.Note;
import com.woozuda.backend.note.entity.type.Visibility;
import jakarta.persistence.EntityManager;

import java.util.List;
import static com.querydsl.core.group.GroupBy.list;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.woozuda.backend.account.entity.QUserEntity.userEntity;
import static com.woozuda.backend.diary.entity.QDiary.diary;
import static com.woozuda.backend.note.entity.QNote.note;
import static com.woozuda.backend.note.entity.QNoteContent.noteContent;

public class SharedNoteRepoImpl implements SharedNoteRepo{

    private final JPAQueryFactory query;

    public SharedNoteRepoImpl(EntityManager em){
        this.query = new JPAQueryFactory(JPQLTemplates.DEFAULT, em);
    }

    @Override
    public List<Note> searchSharedNote(String username) {
        //dto로 반환할까, entity로 반환할까 고민하다가 거의 모든 칼럼을 참고하는 쿼리라서 entity로 해도 괜찮겠다고 생각했습니다.
        //조인이 적은 대신 select 를 한 번 더 하는 방식입니다 ... 성능이 어떤게 좋을지 잘 모르겠어요.
        //코드 난이도 자체도 조금 낮은 편이어서 좋은 것 같긴합니다. (성능만 좀 걱정되는 ... )
        //나중에 쿼리 성능 비교 같은 거 해보면 재미있을 것 같습니다 . ^^
        return query
                .select(note)
                .from(note)
                .where(note.visibility.eq(Visibility.PUBLIC))
                .leftJoin(note.diary, diary)
                .leftJoin(diary.user, userEntity)
                .where(userEntity.username.eq(username))
                .fetch();
    }

    @Override
    public List<String> searchNoteContent(Note note){
        return query
                .select(noteContent.content)
                .from(noteContent)
                .where(noteContent.note.eq(note))
                .orderBy(noteContent.noteOrder.asc())
                .fetch();
    }

}
