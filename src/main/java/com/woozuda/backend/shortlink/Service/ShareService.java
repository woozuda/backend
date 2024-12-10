package com.woozuda.backend.shortlink.Service;


import com.woozuda.backend.note.entity.CommonNote;
import com.woozuda.backend.note.entity.Note;
import com.woozuda.backend.note.entity.QuestionNote;
import com.woozuda.backend.note.entity.RetrospectiveNote;
import com.woozuda.backend.note.entity.type.Visibility;
import com.woozuda.backend.note.repository.NoteRepository;
import com.woozuda.backend.shortlink.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ShareService {

    private final NoteRepository noteRepository;

    @Transactional
    public void makeSharedNote(@RequestBody NoteIdDto noteIdDto) {

        List<Long> noteId = noteIdDto.getId();
        List<Note> notes = noteRepository.findAllById(noteId);

        //System.out.println(noteId);

        for (Note note : notes) {
            note.setVisibility(Visibility.PUBLIC);
        }

        noteRepository.saveAll(notes);
    }


    @Transactional
    public List<SharedNoteDto> getSharedNote(String username) {

        // 밑그림
        // 노트 전체 쿼리 -> where 특정 유저 ->
        List<Note> searchSharedNoteResults = noteRepository.searchSharedNote("woozuda@gmail.com");
        List<SharedNoteDto> dtos = new ArrayList<>();

        for (Note result : searchSharedNoteResults) {

            if (result instanceof CommonNote) {
                CommonNote commonNote = (CommonNote) result;

                dtos.add(new SharedCommonNoteDto(commonNote.getId(), commonNote.getDiary(), commonNote.getTitle(), commonNote.getDate(),
                        noteRepository.searchNoteContent(commonNote), commonNote.getFeeling(), commonNote.getWeather(), commonNote.getSeason()));

            }else if (result instanceof RetrospectiveNote){
                RetrospectiveNote retrospectiveNote = (RetrospectiveNote) result;

                dtos.add(new SharedRetrospectiveNoteDto(retrospectiveNote.getId(), retrospectiveNote.getDiary(), retrospectiveNote.getTitle(), retrospectiveNote.getDate(),
                        noteRepository.searchNoteContent(retrospectiveNote),retrospectiveNote.getType()));

            }else if (result instanceof QuestionNote){
                QuestionNote questionNote = (QuestionNote) result;

                dtos.add(new SharedQuestionNoteDto(questionNote.getId(), questionNote.getDiary(), questionNote.getTitle(), questionNote.getDate(),
                        noteRepository.searchNoteContent(questionNote), questionNote.getQuestion(), questionNote.getFeeling(), questionNote.getWeather(),
                        questionNote.getSeason()));

            }
        }

        return dtos;

    }

}
