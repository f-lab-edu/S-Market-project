package com.flab.s_market.domains.term.repository;

import com.flab.s_market.domains.term.domain.QSubTerm;
import com.flab.s_market.domains.term.dto.response.DetailTermDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

public class SubTermCustomRepositoryImpl implements SubTermCustomRepository{

    private JPAQueryFactory queryFactory;

    public SubTermCustomRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public DetailTermDTO findByTermIdAndVersion(Long termId, Integer version) {
        QSubTerm subTerm = QSubTerm.subTerm;

        return queryFactory
            .select(Projections.constructor(DetailTermDTO.class,
                subTerm.url,
                subTerm.id.version,
                subTerm.term.title
            ))
            .from(subTerm)
            .where(subTerm.id.termId.eq(termId), subTerm.id.version.eq(version))
            .fetchOne();
    }
}
