package com.woozuda.backend.diary.service;

import com.woozuda.backend.account.entity.UserEntity;
import com.woozuda.backend.account.repository.UserRepository;
import com.woozuda.backend.diary.dto.request.DiarySaveRequestDto;
import com.woozuda.backend.diary.dto.response.DiaryDetailResponseDto;
import com.woozuda.backend.diary.dto.response.DiaryIdResponseDto;
import com.woozuda.backend.diary.dto.response.DiaryListResponseDto;
import com.woozuda.backend.diary.dto.response.SingleDiaryResponseDto;
import com.woozuda.backend.diary.entity.Diary;
import com.woozuda.backend.diary.repository.DiaryRepository;
import com.woozuda.backend.note.dto.request.NoteCondRequestDto;
import com.woozuda.backend.note.dto.response.NoteEntryResponseDto;
import com.woozuda.backend.note.dto.response.NoteResponseDto;
import com.woozuda.backend.note.repository.NoteRepository;
import com.woozuda.backend.tag.entity.Tag;
import com.woozuda.backend.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@Transactional
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    private final NoteRepository noteRepository;

    @Transactional(readOnly = true)
    public DiaryListResponseDto getDairyList(String username) {
        List<SingleDiaryResponseDto> diaryList = diaryRepository.searchDiarySummaryList(username);
        return new DiaryListResponseDto(diaryList);
    }

    @Transactional(readOnly = true)
    public DiaryDetailResponseDto getOneDiary(String username, Long diaryId, Pageable pageable) {
        SingleDiaryResponseDto diarySummary = diaryRepository.searchSingleDiarySummary(username, diaryId);

        List<NoteResponseDto> commonNoteDtoList = noteRepository.searchCommonNoteList(List.of(diaryId), new NoteCondRequestDto());
        List<NoteResponseDto> questionNoteDtoList = noteRepository.searchQuestionNoteList(List.of(diaryId), new NoteCondRequestDto());
        List<NoteResponseDto> retrospectiveNoteDtoList = noteRepository.searchRetrospectiveNoteList(List.of(diaryId), new NoteCondRequestDto());

        List<NoteEntryResponseDto> allContent = Stream.of(
                        commonNoteDtoList.stream()
                                .map(noteResponseDto -> new NoteEntryResponseDto("COMMON", noteResponseDto.convertEnum())),
                        questionNoteDtoList.stream()
                                .map(noteResponseDto -> new NoteEntryResponseDto("QUESTION", noteResponseDto.convertEnum())),
                        retrospectiveNoteDtoList.stream()
                                .map(noteResponseDto -> new NoteEntryResponseDto("RETROSPECTIVE", noteResponseDto.convertEnum()))
                ).flatMap(stream -> stream)
                .sorted(Comparator.naturalOrder())
                .toList();

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), allContent.size());

        Page<NoteEntryResponseDto> page;
        if (start > end) {
            page = new PageImpl<>(Collections.emptyList(), pageable, allContent.size());
        } else {
            page = new PageImpl<>(allContent.subList(start, end), pageable, allContent.size());
        }

        return DiaryDetailResponseDto.of(diarySummary, page);
    }

    public DiaryIdResponseDto saveDiary(String username, DiarySaveRequestDto requestDto) {
        UserEntity foundUser = userRepository.findByUsername(username);
        Diary diary = Diary.of(foundUser, requestDto.getImgUrl(), requestDto.getTitle());

        List<String> tags = requestDto.getTags();
        for (String tagName : tags) {
            Tag foundTag = tagRepository.findByName(tagName);
            if (foundTag == null) {
                foundTag = tagRepository.save(Tag.of(tagName));
            }
            diary.addTag(foundTag);
        }

        Diary savedDiary = diaryRepository.save(diary);
        return new DiaryIdResponseDto(savedDiary.getId());
    }

    public DiaryIdResponseDto updateDiary(String username, Long diaryId, DiarySaveRequestDto requestDto) {
        UserEntity foundUser = userRepository.findByUsername(username);

        List<String> tagNames = requestDto.getTags();
        List<Tag> tags = new ArrayList<>();
        for (String tagName : tagNames) {
            Tag foundTag = tagRepository.findByName(tagName);
            if (foundTag == null) {
                foundTag = tagRepository.save(Tag.of(tagName));
            }
            tags.add(foundTag);
        }

        Optional<Diary> foundDiary = diaryRepository.findByIdAndUser(diaryId, foundUser);
        if (foundDiary.isPresent()) {
            Diary diary = foundDiary.get();
            diary.change(requestDto.getTitle(), tags, requestDto.getImgUrl());
        }

        return new DiaryIdResponseDto(foundDiary.get().getId());
    }

    //TODO 일기 기능까지 추가한 뒤에, 다이어리 삭제하면 내부 일기까지 전부 삭제하도록 변경
    //TODO 배포 환경에서는 Diary 를 지우면 관련 DiaryTag 도 모두 지워질 수 있도록 데이터베이스 차원에서 cascade delete 설정
    public void removeDiary(String username, Long diaryId) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new IllegalArgumentException("Diary Not Found"));
        if (!diary.getUser().getUsername().equals(username)) {
            throw new IllegalArgumentException("This diary does not belong to the user.");
        }

        diaryRepository.deleteById(diaryId);
    }
}
