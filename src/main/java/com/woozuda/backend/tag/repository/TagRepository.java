package com.woozuda.backend.tag.repository;

import com.woozuda.backend.tag.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {

    Tag findByName(String name);

}
