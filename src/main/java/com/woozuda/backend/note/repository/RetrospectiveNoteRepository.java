package com.woozuda.backend.note.repository;

import com.woozuda.backend.note.entity.QuestionNote;
import com.woozuda.backend.note.entity.RetrospectiveNote;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RetrospectiveNoteRepository extends JpaRepository<RetrospectiveNote, Long> {

    @Override
    @EntityGraph(attributePaths = {"diary", "noteContents"})
    Optional<RetrospectiveNote> findById(Long aLong);

}
