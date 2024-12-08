package com.woozuda.backend.note.service;

import com.woozuda.backend.diary.dto.response.NoteIdResponseDto;
import com.woozuda.backend.diary.entity.Diary;
import com.woozuda.backend.diary.repository.DiaryRepository;
import com.woozuda.backend.note.dto.request.QuestionNoteSaveRequestDto;
import com.woozuda.backend.note.dto.response.NoteResponseDto;
import com.woozuda.backend.note.entity.NoteContent;
import com.woozuda.backend.note.entity.Question;
import com.woozuda.backend.note.entity.QuestionNote;
import com.woozuda.backend.note.entity.type.Feeling;
import com.woozuda.backend.note.entity.type.Season;
import com.woozuda.backend.note.entity.type.Weather;
import com.woozuda.backend.note.repository.NoteRepository;
import com.woozuda.backend.note.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static com.woozuda.backend.note.entity.type.Visibility.PRIVATE;

@Service
@Transactional
@RequiredArgsConstructor
public class QuestionNoteService {

    private final NoteRepository noteRepository;
    private final DiaryRepository diaryRepository;
    private final QuestionRepository questionRepository;

    public NoteIdResponseDto saveQuestionNote(String username, QuestionNoteSaveRequestDto requestDto) {
        Diary foundDiary = diaryRepository.searchDiary(requestDto.getDiaryId(), username);
        if (foundDiary == null) {
            throw new IllegalArgumentException("Diary not found.");
        }

        Question foundQuestion = questionRepository.findByTodayDate(LocalDate.parse(requestDto.getDate()));

        QuestionNote questionNote = QuestionNote.of(foundDiary,
                requestDto.getTitle(),
                LocalDate.parse(requestDto.getDate()),
                PRIVATE,
                foundQuestion,
                Feeling.fromName(requestDto.getFeeling()),
                Weather.fromName(requestDto.getWeather()),
                Season.fromName(requestDto.getSeason())
        );
        QuestionNote savedQuestionNote= noteRepository.save(questionNote);

        NoteContent noteContent = NoteContent.of(1, requestDto.getContent());
        savedQuestionNote.addContent(noteContent);

        foundDiary.addNote(savedQuestionNote.getDate());

        return NoteIdResponseDto.of(savedQuestionNote.getId());
    }

    public NoteResponseDto getQuestionNote(String username, Long noteId) {
        Diary foundDiary = diaryRepository.searchDiary(noteId, username);
        if (foundDiary == null) {
            throw new IllegalArgumentException("Diary not found.");
        }

        NoteResponseDto responseDto = noteRepository.searchQuestionNote(noteId);
        return responseDto.convertEnum();
    }
}
