package com.woozuda.backend.note.service;

import com.woozuda.backend.diary.dto.response.NoteIdResponseDto;
import com.woozuda.backend.diary.entity.Diary;
import com.woozuda.backend.diary.repository.DiaryRepository;
import com.woozuda.backend.note.dto.request.RetrospectiveNoteSaveRequestDto;
import com.woozuda.backend.note.dto.response.NoteResponseDto;
import com.woozuda.backend.note.entity.NoteContent;
import com.woozuda.backend.note.entity.RetrospectiveNote;
import com.woozuda.backend.note.entity.type.Framework;
import com.woozuda.backend.note.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static com.woozuda.backend.note.entity.type.Visibility.PRIVATE;

@Service
@Transactional
@RequiredArgsConstructor
public class RetrospectiveNoteService {

    private final NoteRepository noteRepository;
    private final DiaryRepository diaryRepository;

    public NoteIdResponseDto saveRetrospectiveNote(String username, RetrospectiveNoteSaveRequestDto requestDto) {
        Diary foundDiary = diaryRepository.searchDiary(requestDto.getDiaryId(), username);
        if (foundDiary == null) {
            throw new IllegalArgumentException("Diary not found.");
        }

        RetrospectiveNote retrospectiveNote = RetrospectiveNote.of(foundDiary,
                requestDto.getTitle(),
                LocalDate.parse(requestDto.getDate()),
                PRIVATE,
                Framework.valueOf(requestDto.getType())
        );

        RetrospectiveNote savedRetrospectiveNote = noteRepository.save(retrospectiveNote);

        List<String> content = requestDto.getContent();
        for (int i = 0; i < content.size(); i++) {
            NoteContent noteContent = NoteContent.of(i + 1, content.get(i));
            savedRetrospectiveNote.addContent(noteContent);
        }

        foundDiary.addNote(savedRetrospectiveNote.getDate());

        return NoteIdResponseDto.of(savedRetrospectiveNote.getId());
    }

    public NoteResponseDto getRetrospectiveNote(String username, Long noteId) {
        Diary foundDiary = diaryRepository.searchDiary(noteId, username);
        if (foundDiary == null) {
            throw new IllegalArgumentException("Diary not found.");
        }

        NoteResponseDto responseDto = noteRepository.searchRetrospectiveNote(noteId);
        return responseDto.convertEnum();
    }
}
