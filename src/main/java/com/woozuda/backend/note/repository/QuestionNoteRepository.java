package com.woozuda.backend.note.repository;

import com.woozuda.backend.note.entity.CommonNote;
import com.woozuda.backend.note.entity.QuestionNote;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuestionNoteRepository extends JpaRepository<QuestionNote, Long> {

    @Override
    @EntityGraph(attributePaths = {"diary", "noteContents"})
    Optional<QuestionNote> findById(Long aLong);
}
