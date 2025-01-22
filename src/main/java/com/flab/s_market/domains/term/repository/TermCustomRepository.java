package com.flab.s_market.domains.term.repository;

import com.flab.s_market.domains.term.domain.SubTerm;
import com.flab.s_market.domains.term.domain.Term;
import java.util.List;
import java.util.Optional;

public interface TermCustomRepository {
    List<Term> findTermByVersionWithJoin(Integer version);
    Optional<SubTerm> findByTitleAndVersionWithJoin(String title, Integer version);
}
