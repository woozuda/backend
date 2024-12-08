package com.woozuda.backend.note.repository;

import com.woozuda.backend.note.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long>, CustomNoteRepository {
}
