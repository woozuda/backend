package com.woozuda.backend.shortlink.repository;

import com.woozuda.backend.account.entity.UserEntity;
import com.woozuda.backend.shortlink.entity.ShortLink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShortLinkRepository extends JpaRepository<ShortLink, Long> {

    ShortLink findByUrl(String url);

    ShortLink findByUserEntity(UserEntity userEntity);
}
