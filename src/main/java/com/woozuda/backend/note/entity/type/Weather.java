package com.woozuda.backend.note.entity.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

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

    private static final Map<String, Weather> store = new HashMap<>();

    static {
        for (Weather weather : Weather.values()) {
            store.put(weather.name, weather);
        }
    }

    public static Weather fromName(String name) {
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
