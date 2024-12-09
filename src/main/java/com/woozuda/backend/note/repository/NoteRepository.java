package com.woozuda.backend.note.repository;

import com.woozuda.backend.forai.repository.CustomNoteRepoForAi;
import com.woozuda.backend.note.entity.Note;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long>, CustomNoteRepository, CustomNoteRepoForAi {

    @Query("select n.date from Note n where n.diary.id in :idList")
    List<LocalDate> findDateByDiaryIds(@Param("idList") List<Long> idList);

    @Override
    @EntityGraph(attributePaths = {"noteContents"})
    List<Note> findAllById(Iterable<Long> longs);
}
