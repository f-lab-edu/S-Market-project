package com.flab.s_market.domains.term.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserSubTerm is a Querydsl query type for UserSubTerm
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserSubTerm extends EntityPathBase<UserSubTerm> {

    private static final long serialVersionUID = 1703984846L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserSubTerm userSubTerm = new QUserSubTerm("userSubTerm");

    public final DateTimePath<java.time.LocalDateTime> agreeDate = createDateTime("agreeDate", java.time.LocalDateTime.class);

    public final QUserSubTermId id;

    public final com.flab.s_market.domains.user.domain.QUser user;

    public QUserSubTerm(String variable) {
        this(UserSubTerm.class, forVariable(variable), INITS);
    }

    public QUserSubTerm(Path<? extends UserSubTerm> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserSubTerm(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserSubTerm(PathMetadata metadata, PathInits inits) {
        this(UserSubTerm.class, metadata, inits);
    }

    public QUserSubTerm(Class<? extends UserSubTerm> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QUserSubTermId(forProperty("id"), inits.get("id")) : null;
        this.user = inits.isInitialized("user") ? new com.flab.s_market.domains.user.domain.QUser(forProperty("user")) : null;
    }

}

