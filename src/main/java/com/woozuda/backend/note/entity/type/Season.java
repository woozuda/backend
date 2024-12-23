package com.woozuda.backend.note.entity.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Getter
public enum Season {

    SPRING("봄"),
    SUMMER("여름"),
    FALL("가을"),
    WINTER("겨울");

    private final String name;

    private static final Map<String, Season> store = new HashMap<>();

    static {
        for (Season season : Season.values()) {
            store.put(season.name, season);
        }
    }

    public static Season fromName(String name) {
        return store.get(name);
    }

    public static String fromValue(String value) {
        return valueOf(value).name;
    }

    @Override
    public String toString() {
        return name;
    }
}
