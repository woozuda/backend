package com.woozuda.backend.forai.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CountRecallDto {
    private long ffs;
    private long kpt;
    private long pmi;
    private long scs;
    @Override
    public String toString() {
        return "CountRecallDto{" +
                "ffs=" + ffs +
                ", kpt=" + kpt +
                ", pmi=" + pmi +
                ", scs=" + scs +
                '}';
    }
}
