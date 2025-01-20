package com.flab.s_market.domains.term.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserSubTermId is a Querydsl query type for UserSubTermId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QUserSubTermId extends BeanPath<UserSubTermId> {

    private static final long serialVersionUID = 1146899593L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserSubTermId userSubTermId = new QUserSubTermId("userSubTermId");

    public final QSubTerm subTerm;

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QUserSubTermId(String variable) {
        this(UserSubTermId.class, forVariable(variable), INITS);
    }

    public QUserSubTermId(Path<? extends UserSubTermId> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserSubTermId(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserSubTermId(PathMetadata metadata, PathInits inits) {
        this(UserSubTermId.class, metadata, inits);
    }

    public QUserSubTermId(Class<? extends UserSubTermId> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.subTerm = inits.isInitialized("subTerm") ? new QSubTerm(forProperty("subTerm"), inits.get("subTerm")) : null;
    }

}

