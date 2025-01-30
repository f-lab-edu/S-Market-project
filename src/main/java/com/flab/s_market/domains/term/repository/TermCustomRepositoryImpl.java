package com.flab.s_market.domains.term.repository;

import com.flab.s_market.domains.term.domain.QSubTerm;
import com.flab.s_market.domains.term.domain.QTerm;
import com.flab.s_market.domains.term.domain.SubTerm;
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

    @Override
    public List<SubTerm> findByTermIdAndVersionWithJoin() {
        QTerm term = QTerm.term;
        QSubTerm subTerm = QSubTerm.subTerm;

        return queryFactory
                .select(subTerm)
                .from(subTerm)
                .where(subTerm.term.in(
                    JPAExpressions
                        .selectFrom(term)
                        .where(term.isRequired.eq(true))
                ), subTerm.id.version.eq(
                    queryFactory.select(subTerm.id.version.max())
                        .from(subTerm)
                ))
                .fetch();
    }
}
