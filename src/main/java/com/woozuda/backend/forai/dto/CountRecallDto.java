package com.woozuda.backend.forai.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CountRecallDto {
    private Integer ffs;
    private Integer kpt;
    private Integer pmi;
    private Integer scs;

}
