package com.woozuda.backend.diary.repository;

import com.woozuda.backend.diary.dto.response.SingleDiaryResponseDto;

import java.util.List;

public interface CustomDiaryRepository {

    List<SingleDiaryResponseDto> searchDiarySummaryList(String username);

}
