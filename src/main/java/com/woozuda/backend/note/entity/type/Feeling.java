package com.woozuda.backend.note.entity.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Feeling {

    JOY("기쁨"),
    CONTENT("만족"),
    HAPPINESS("행복"),
    NEUTRAL("보통"),
    DISSATISFACTION("불만"),
    ANGER("분노"),
    TIREDNESS("피곤"),
    SADNESS("슬픔");

    private final String name;

    @Override
    public String toString() {
        return name;
    }
}
