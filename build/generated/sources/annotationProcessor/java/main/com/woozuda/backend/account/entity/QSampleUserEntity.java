package com.woozuda.backend.account.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSampleUserEntity is a Querydsl query type for SampleUserEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSampleUserEntity extends EntityPathBase<UserEntity> {

    private static final long serialVersionUID = 131795622L;

    public static final QSampleUserEntity sampleUserEntity = new QSampleUserEntity("sampleUserEntity");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath password = createString("password");

    public final StringPath role = createString("role");

    public final StringPath username = createString("username");

    public QSampleUserEntity(String variable) {
        super(UserEntity.class, forVariable(variable));
    }

    public QSampleUserEntity(Path<? extends UserEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSampleUserEntity(PathMetadata metadata) {
        super(UserEntity.class, metadata);
    }

}

