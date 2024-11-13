package com.woozuda.backend.account.dto;


import com.woozuda.backend.account.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinDTO {
    String username;
    String password;
}
