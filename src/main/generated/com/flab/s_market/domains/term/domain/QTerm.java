package com.flab.s_market.domains.term.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTerm is a Querydsl query type for Term
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTerm extends EntityPathBase<Term> {

    private static final long serialVersionUID = 110004927L;

    public static final QTerm term = new QTerm("term");

    public final BooleanPath additional = createBoolean("additional");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isRequired = createBoolean("isRequired");

    public final StringPath title = createString("title");

    public QTerm(String variable) {
        super(Term.class, forVariable(variable));
    }

    public QTerm(Path<? extends Term> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTerm(PathMetadata metadata) {
        super(Term.class, metadata);
    }

}

