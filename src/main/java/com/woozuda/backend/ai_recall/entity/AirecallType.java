package com.woozuda.backend.ai_recall.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Getter
public enum AirecallType {
    FFS, // 4FS
    KTP,
    PMI,
    SCS;

    public static AirecallType fromString(String value) {
        log.info("원본 enum 값: (" + value + ")"); // 원본 로그

        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("입력 값이 비어있음");
        }

        // 앞의 3글자 추출
        String enumValue = value.trim().substring(0, 3).toUpperCase(); // 앞 3글자 추출 및 대문자 변환
        log.info("추출된 3글자: [" + enumValue + "]");

        // Enum 값과 비교
        switch (enumValue) {
            case "4FS":
                return AirecallType.FFS;
            case "KTP":
                return AirecallType.KTP;
            case "PMI":
                return AirecallType.PMI;
            case "SCS":
                return AirecallType.SCS;
            default:
                log.error("Enum 매핑 실패: " + enumValue);
                throw new IllegalArgumentException("알 수 없는 Enum 값: " + enumValue);
        }
    }
}
