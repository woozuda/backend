package com.woozuda.backend.diary.service;

import com.woozuda.backend.account.entity.UserEntity;
import com.woozuda.backend.account.repository.UserRepository;
import com.woozuda.backend.diary.dto.request.DiarySaveRequestDto;
import com.woozuda.backend.diary.dto.response.DiaryIdResponseDto;
import com.woozuda.backend.diary.dto.response.DiaryListResponseDto;
import com.woozuda.backend.diary.dto.response.SingleDiaryResponseDto;
import com.woozuda.backend.diary.entity.Diary;
import com.woozuda.backend.diary.repository.DiaryRepository;
import com.woozuda.backend.tag.entity.Tag;
import com.woozuda.backend.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;

    @Transactional(readOnly = true)
    public DiaryListResponseDto getDairyList(String username) {
        List<SingleDiaryResponseDto> diaryList = diaryRepository.searchDiarySummaryList(username);
        DiaryListResponseDto responseDto = new DiaryListResponseDto(diaryList);

        return null;
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
    public void removeDiary(String username, Long diaryId) {
        diaryRepository.deleteUserDiary(diaryId, username);
    }
}
