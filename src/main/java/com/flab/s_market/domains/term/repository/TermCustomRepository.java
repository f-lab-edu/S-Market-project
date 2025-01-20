package com.flab.s_market.domains.term.repository;

import com.flab.s_market.domains.term.domain.Term;
import java.util.List;

public interface TermCustomRepository {
    List<Term> findTermByVersionWithJoin(Integer version);
}
