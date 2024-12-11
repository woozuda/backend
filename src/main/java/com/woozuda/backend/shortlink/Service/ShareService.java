package com.woozuda.backend.shortlink.Service;


import com.woozuda.backend.note.entity.*;
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

        //
        List<SharedNoteDto> commonNoteResults = noteRepository.searchSharedCommonNote("woozuda@gmail.com");
        List<SharedNoteDto> questionNoteResults = noteRepository.searchSharedQuestionNote("woozuda@gmail.com");
        List<SharedNoteDto> retrospectiveNoteResults = noteRepository.searchSharedRetrospectiveNote("woozuda@gmail.com");

        List<SharedNoteDto> allSharedNotes = new ArrayList<>();
        allSharedNotes.addAll(commonNoteResults);
        allSharedNotes.addAll(questionNoteResults);
        allSharedNotes.addAll(retrospectiveNoteResults);



        for (SharedNoteDto sharedNote : allSharedNotes) {

            if (sharedNote instanceof SharedCommonNoteDto) {
                SharedCommonNoteDto commonNote = (SharedCommonNoteDto) sharedNote;
                System.out.println(commonNote.getTitle());

            }else if (sharedNote instanceof SharedRetrospectiveNoteDto){
                SharedRetrospectiveNoteDto retrospectiveNote = (SharedRetrospectiveNoteDto) sharedNote;
                System.out.println(retrospectiveNote.getType());
                System.out.println(retrospectiveNote.getNoteContents());
            }else if (sharedNote instanceof SharedQuestionNoteDto){
                SharedQuestionNoteDto questionNote = (SharedQuestionNoteDto) sharedNote;
            }
        }


        return allSharedNotes;

    }

    public List<String> extractContent(List<NoteContent> notecontents){

        return null;
    }

}
