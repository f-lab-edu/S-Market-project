package com.flab.s_market.domains.term.repository;

import com.flab.s_market.domains.term.domain.QSubTerm;
import com.flab.s_market.domains.term.domain.QTerm;
import com.flab.s_market.domains.term.domain.SubTerm;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;

public class TermCustomRepositoryImpl implements TermCustomRepository{
    private final JPAQueryFactory queryFactory;

    public TermCustomRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<SubTerm> findByTermIdAndVersionWithJoin() {
        QTerm term = QTerm.term;
        QSubTerm subTerm = QSubTerm.subTerm;

        return queryFactory
                .select(subTerm)
                .from(subTerm)
                .where(subTerm.id.version.eq(
                    queryFactory
                        .select(subTerm.id.version.max())
                        .from(subTerm)
                ))
                .fetch();
    }
}
