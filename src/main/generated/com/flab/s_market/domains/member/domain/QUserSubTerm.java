package com.flab.s_market.domains.member.domain;

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
public class QUserSubTerm extends EntityPathBase<MemberSubTerm> {

    private static final long serialVersionUID = -310950641L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserSubTerm userSubTerm = new QUserSubTerm("userSubTerm");

    public final com.flab.s_market.common.entity.QBaseEntity _super = new com.flab.s_market.common.entity.QBaseEntity(this);

    public final BooleanPath agree = createBoolean("agree");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final QUserSubTermId id;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final QUser user;

    public QUserSubTerm(String variable) {
        this(MemberSubTerm.class, forVariable(variable), INITS);
    }

    public QUserSubTerm(Path<? extends MemberSubTerm> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserSubTerm(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserSubTerm(PathMetadata metadata, PathInits inits) {
        this(MemberSubTerm.class, metadata, inits);
    }

    public QUserSubTerm(Class<? extends MemberSubTerm> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QUserSubTermId(forProperty("id"), inits.get("id")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

