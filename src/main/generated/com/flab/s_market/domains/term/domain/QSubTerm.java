package com.flab.s_market.domains.term.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSubTerm is a Querydsl query type for SubTerm
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSubTerm extends EntityPathBase<SubTerm> {

    private static final long serialVersionUID = -348124263L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSubTerm subTerm = new QSubTerm("subTerm");

    public final QSubTermId id;

    public final QTerm term;

    public final StringPath url = createString("url");

    public QSubTerm(String variable) {
        this(SubTerm.class, forVariable(variable), INITS);
    }

    public QSubTerm(Path<? extends SubTerm> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSubTerm(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSubTerm(PathMetadata metadata, PathInits inits) {
        this(SubTerm.class, metadata, inits);
    }

    public QSubTerm(Class<? extends SubTerm> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QSubTermId(forProperty("id")) : null;
        this.term = inits.isInitialized("term") ? new QTerm(forProperty("term")) : null;
    }

}

