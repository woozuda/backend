package com.woozuda.backend.note.entity.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

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

    private static final Map<String, Feeling> store = new HashMap<>();

    static {
        for (Feeling feeling : Feeling.values()) {
            store.put(feeling.name, feeling);
        }
    }

    public static Feeling fromName(String name) {
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
