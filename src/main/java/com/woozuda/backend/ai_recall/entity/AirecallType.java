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

}
