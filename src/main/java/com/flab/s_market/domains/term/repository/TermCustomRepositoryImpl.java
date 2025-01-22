package com.flab.s_market.domains.term.repository;

import com.flab.s_market.domains.term.domain.QSubTerm;
import com.flab.s_market.domains.term.domain.QTerm;
import com.flab.s_market.domains.term.domain.SubTerm;
import com.flab.s_market.domains.term.domain.Term;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

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

    @Override
    public Optional<SubTerm> findByTitleAndVersionWithJoin(String title, Integer version) {
        QTerm term = QTerm.term;
        QSubTerm subTerm = QSubTerm.subTerm;

        return Optional.ofNullable(
            queryFactory
                .select(subTerm)
                .from(subTerm)
                .where(subTerm.term.in(
                    JPAExpressions
                        .selectFrom(term)
                        .where(term.title.eq(title))
                ), subTerm.id.version.eq(version))
                .fetchOne()
        );

    }
}
