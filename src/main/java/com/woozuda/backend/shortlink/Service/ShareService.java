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
import java.util.stream.Stream;

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
    public SharedNoteResponseDto getSharedNote(String username) {

        //
        List<SharedNoteDto> commonNoteResults = noteRepository.searchSharedCommonNote(username);
        List<SharedNoteDto> questionNoteResults = noteRepository.searchSharedQuestionNote(username);
        List<SharedNoteDto> retrospectiveNoteResults = noteRepository.searchSharedRetrospectiveNote(username);

        /*
        List<SharedNoteDto> allSharedNotes = new ArrayList<>();
        allSharedNotes.addAll(commonNoteResults);
        allSharedNotes.addAll(questionNoteResults);
        allSharedNotes.addAll(retrospectiveNoteResults);
        */

        List<SharedNoteTypeDto> allSharedNotes = Stream.concat(
                commonNoteResults.stream().map(note -> new SharedNoteTypeDto("COMMON", note)),
                Stream.concat(
                        questionNoteResults.stream().map(note -> new SharedNoteTypeDto("QUESTION", note)),
                        retrospectiveNoteResults.stream().map(note -> new SharedNoteTypeDto("RETROSPECTIVE", note))
                )
        ).collect(Collectors.toList());
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
        Map<LocalDate, List<SharedNoteTypeDto>> allSharedNotesByDate = allSharedNotes.stream()
                .collect(Collectors.groupingBy(dto -> dto.getNote().getDate()));
        /*
        Map<LocalDate, List<SharedNoteDto>> allSharedNotesByDate = allSharedNotes.stream()
                .collect(Collectors.groupingBy(SharedNoteDto::getDate));
        */
        List<SharedNoteByDateDto> sharedNotesByDateDtoList = allSharedNotesByDate.entrySet().stream()
                .map(entry -> new SharedNoteByDateDto(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        // totalCount 반영

        return new SharedNoteResponseDto(allSharedNotes.size(), sharedNotesByDateDtoList);

    }

    public List<String> extractContent(List<NoteContent> notecontents){

        return null;
    }

}
