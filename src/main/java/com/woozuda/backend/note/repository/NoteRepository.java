package com.woozuda.backend.note.repository;

import com.woozuda.backend.note.entity.Note;
import com.woozuda.backend.note.entity.type.Visibility;
import com.woozuda.backend.shortlink.repository.SharedNoteRepo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long>, CustomNoteRepository, SharedNoteRepo {
}
