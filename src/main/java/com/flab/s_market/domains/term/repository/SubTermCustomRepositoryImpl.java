package com.flab.s_market.domains.term.repository;

import com.flab.s_market.domains.term.domain.QSubTerm;
import com.flab.s_market.domains.term.domain.SubTerm;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.Optional;

public class SubTermCustomRepositoryImpl implements SubTermCustomRepository{

    private JPAQueryFactory queryFactory;

    public SubTermCustomRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Optional<SubTerm> findByTermIdAndVersion(Long termId, Integer version) {
        QSubTerm subTerm = QSubTerm.subTerm;

        SubTerm sub = queryFactory
            .select(subTerm)
            .from(subTerm)
            .where(subTerm.id.termId.eq(termId), subTerm.id.version.eq(version))
            .fetchOne();

        return Optional.ofNullable(sub);
        /*if(sub == null){
            return Optional.empty();
        }else{
            return Optional.of(sub);
        }*/
    }
}
