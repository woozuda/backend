package com.woozuda.backend.note.entity.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Season {

    SPRING("봄"),
    SUMMER("여름"),
    FALL("가을"),
    WINTER("겨울");

    private final String name;

    @Override
    public String toString() {
        return name;
    }
}
