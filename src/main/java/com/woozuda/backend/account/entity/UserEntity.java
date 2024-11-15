package com.woozuda.backend.account.entity;

import com.woozuda.backend.account.dto.JoinDTO;
import jakarta.persistence.*;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String role;

    public static UserEntity transEntity(JoinDTO joinDTO){
        return new UserEntity(null, joinDTO.getUsername(), joinDTO.getPassword(), "ROLE_ADMIN");
    }
}
