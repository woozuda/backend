package com.woozuda.backend.account.repository;

import com.woozuda.backend.account.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity,Long> {

    Boolean existsByUsername(String username);

    UserEntity findByUsername(String username);
}
