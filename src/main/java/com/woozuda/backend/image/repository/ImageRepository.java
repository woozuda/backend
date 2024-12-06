package com.woozuda.backend.image.repository;

import com.woozuda.backend.image.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image,Long> {
}
