package com.woozuda.backend.note.repository;

import com.woozuda.backend.note.entity.CommonNote;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommonNoteRepository extends JpaRepository<CommonNote, Long> {

    /*
     Note 조회 시 관련된 NoteContent 도 모두 페치 조인으로 조회
     attributePaths에 지정한 연관 필드가 상위 클래스(Note), 하위 클래스(CommonNote) 중 어느 곳에 있더라도 문제없이 동작
     -> TODO 관련 내용 정리
     */
    @Override
    @EntityGraph(attributePaths = {"diary", "noteContents"})
    Optional<CommonNote> findById(Long aLong);

}
