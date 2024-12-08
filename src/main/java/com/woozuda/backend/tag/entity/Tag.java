package com.woozuda.backend.tag.entity;

import com.woozuda.backend.global.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
        name = "tag",
        uniqueConstraints = {@UniqueConstraint(name = "unique_name", columnNames = "name")},
        indexes = {@Index(columnList = "name")} //ddl-auto:none 이면 동작 X
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long id;

    @Column(length = 100, nullable = false)
    private String name;

    private Tag(String name) {
        this.name = name;
    }

    public static Tag of(String name) {
        return new Tag(name);
    }
}
