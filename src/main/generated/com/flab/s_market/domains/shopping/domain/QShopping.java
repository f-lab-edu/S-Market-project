package com.flab.s_market.domains.shopping.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QShopping is a Querydsl query type for Shopping
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QShopping extends EntityPathBase<Shopping> {

    private static final long serialVersionUID = -546181705L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QShopping shopping = new QShopping("shopping");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.flab.s_market.domains.user.domain.QUser user;

    public QShopping(String variable) {
        this(Shopping.class, forVariable(variable), INITS);
    }

    public QShopping(Path<? extends Shopping> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QShopping(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QShopping(PathMetadata metadata, PathInits inits) {
        this(Shopping.class, metadata, inits);
    }

    public QShopping(Class<? extends Shopping> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.flab.s_market.domains.user.domain.QUser(forProperty("user")) : null;
    }

}

