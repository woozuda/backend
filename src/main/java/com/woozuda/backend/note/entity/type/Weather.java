package com.woozuda.backend.note.entity.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Weather {

    SUNNY("화창"),
    CLEAR("맑음"),
    PARTLY_CLOUDY("구름많음"),
    CLOUDY("흐림"),
    RAIN("비"),
    SNOW("눈"),
    WINDY("바람"),
    THUNDERSTORM("천둥번개");

    private final String name;

    @Override
    public String toString() {
        return name;
    }
}
