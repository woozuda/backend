package com.woozuda.backend.mypage.dto;

import com.woozuda.backend.account.entity.AiType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AiCreationDto {
    AiType aiType;
}
