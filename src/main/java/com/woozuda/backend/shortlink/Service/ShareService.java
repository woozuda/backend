package com.woozuda.backend.shortlink.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woozuda.backend.note.entity.*;
import com.woozuda.backend.note.entity.type.Visibility;
import com.woozuda.backend.note.repository.NoteRepository;
import com.woozuda.backend.shortlink.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public List<SharedNoteByDateDto> getSharedNote(String username) {

        //
        List<SharedNoteDto> commonNoteResults = noteRepository.searchSharedCommonNote("woozuda@gmail.com");
        List<SharedNoteDto> questionNoteResults = noteRepository.searchSharedQuestionNote("woozuda@gmail.com");
        List<SharedNoteDto> retrospectiveNoteResults = noteRepository.searchSharedRetrospectiveNote("woozuda@gmail.com");

        List<SharedNoteDto> allSharedNotes = new ArrayList<>();
        allSharedNotes.addAll(commonNoteResults);
        allSharedNotes.addAll(questionNoteResults);
        allSharedNotes.addAll(retrospectiveNoteResults);

        /*
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
        */

        // 날짜 별로 group by
        Map<LocalDate, List<SharedNoteDto>> allSharedNotesByDate = allSharedNotes.stream()
                .collect(Collectors.groupingBy(SharedNoteDto::getDate));

        List<SharedNoteByDateDto> sharedNotesByDateDtoList = allSharedNotesByDate.entrySet().stream()
                .map(entry -> new SharedNoteByDateDto(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        return sharedNotesByDateDtoList;

    }

    public List<String> extractContent(List<NoteContent> notecontents){

        return null;
    }

}
