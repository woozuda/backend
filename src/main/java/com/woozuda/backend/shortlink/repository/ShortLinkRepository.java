package com.woozuda.backend.shortlink.repository;

import com.woozuda.backend.shortlink.entity.ShortLink;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShortLinkRepository extends JpaRepository<ShortLink, Long> {

    findByUrl(String url)
}
