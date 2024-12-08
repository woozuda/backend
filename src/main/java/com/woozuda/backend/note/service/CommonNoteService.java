package com.woozuda.backend.note.service;

import com.woozuda.backend.diary.dto.response.NoteIdResponseDto;
import com.woozuda.backend.diary.entity.Diary;
import com.woozuda.backend.diary.repository.DiaryRepository;
import com.woozuda.backend.note.dto.request.CommonNoteSaveRequestDto;
import com.woozuda.backend.note.dto.response.NoteResponseDto;
import com.woozuda.backend.note.entity.CommonNote;
import com.woozuda.backend.note.entity.NoteContent;
import com.woozuda.backend.note.entity.type.Feeling;
import com.woozuda.backend.note.entity.type.Season;
import com.woozuda.backend.note.entity.type.Weather;
import com.woozuda.backend.note.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static com.woozuda.backend.note.entity.type.Visibility.PRIVATE;

@Service
@Transactional
@RequiredArgsConstructor
public class CommonNoteService {

    private final NoteRepository noteRepository;
    private final DiaryRepository diaryRepository;

    public NoteIdResponseDto saveCommonNote(String username, CommonNoteSaveRequestDto requestDto) {
        Diary foundDiary = diaryRepository.searchDiary(requestDto.getDiaryId(), username);
        if (foundDiary == null) {
            throw new IllegalArgumentException("Diary not found.");
        }
        CommonNote commonNote = CommonNote.of(foundDiary,
                requestDto.getTitle(),
                LocalDate.parse(requestDto.getDate()),
                PRIVATE,
                Feeling.fromName(requestDto.getFeeling()),
                Weather.fromName(requestDto.getWeather()),
                Season.fromName(requestDto.getSeason())
        );
        CommonNote savedCommonNote = noteRepository.save(commonNote);

        NoteContent noteContent = NoteContent.of(1, requestDto.getContent());
        savedCommonNote.addContent(noteContent);

        foundDiary.addNote(savedCommonNote.getDate());

        return NoteIdResponseDto.of(savedCommonNote.getId());
    }

    public NoteResponseDto getCommonNote(String username, Long noteId) {
        Diary foundDiary = diaryRepository.searchDiary(noteId, username);
        if (foundDiary == null) {
            throw new IllegalArgumentException("Diary not found.");
        }

        NoteResponseDto responseDto = noteRepository.searchCommonNote(noteId);
        return responseDto.convertEnum();
    }
}
