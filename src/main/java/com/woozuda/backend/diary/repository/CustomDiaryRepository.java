package com.woozuda.backend.diary.repository;

import com.woozuda.backend.diary.dto.response.SingleDiaryResponseDto;

import java.util.List;

public interface CustomDiaryRepository {

    List<SingleDiaryResponseDto> searchDiarySummaryList(String username);

    SingleDiaryResponseDto searchSingleDiarySummary(String username, Long diaryId);

    List<Long> searchDiaryIdList(String username);
}
