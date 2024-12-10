package com.woozuda.backend.shortlink.repository;

import com.woozuda.backend.note.entity.Note;

import java.util.List;

public interface SharedNoteRepo {
    List<Note> searchSharedNote(String username);

    List<String> searchNoteContent(Note note);

}
