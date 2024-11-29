package com.woozuda.backend.note.entity;

import com.woozuda.backend.note.entity.type.Framework;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@DiscriminatorValue("RETROSPECTIVE")
@Table(name = "retrospective_note")
public class RetrospectiveNote extends Note {

    @Enumerated(EnumType.STRING)
    @Column(length = 100, nullable = false)
    private Framework type;

}
