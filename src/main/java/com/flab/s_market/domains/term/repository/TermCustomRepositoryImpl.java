package com.flab.s_market.domains.term.repository;

import com.flab.s_market.domains.term.domain.QSubTerm;
import com.flab.s_market.domains.term.domain.QTerm;
import com.flab.s_market.domains.term.domain.Term;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;

public class TermCustomRepositoryImpl implements TermCustomRepository{
    private final JPAQueryFactory queryFactory;

    public TermCustomRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Term> findTermByVersionWithJoin(Integer version) {
        QTerm term = QTerm.term;
        QSubTerm subTerm = QSubTerm.subTerm;

        List<Term> terms = queryFactory
            .select(subTerm.term)
            .from(subTerm)
            .join(subTerm.term, term)
            .where(subTerm.id.version.eq(version))
            .fetch();
        return terms;
    }
}
