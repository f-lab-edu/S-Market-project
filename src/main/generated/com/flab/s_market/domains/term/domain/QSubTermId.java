package com.flab.s_market.domains.term.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSubTermId is a Querydsl query type for SubTermId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QSubTermId extends BeanPath<SubTermId> {

    private static final long serialVersionUID = 460034708L;

    public static final QSubTermId subTermId = new QSubTermId("subTermId");

    public final NumberPath<Long> termId = createNumber("termId", Long.class);

    public final NumberPath<Integer> version = createNumber("version", Integer.class);

    public QSubTermId(String variable) {
        super(SubTermId.class, forVariable(variable));
    }

    public QSubTermId(Path<? extends SubTermId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSubTermId(PathMetadata metadata) {
        super(SubTermId.class, metadata);
    }

}

