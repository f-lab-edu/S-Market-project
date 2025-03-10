package com.flab.s_market.domains.term.repository;

import com.flab.s_market.domains.term.domain.SubTerm;
import java.util.List;

public interface TermCustomRepository {
    List<SubTerm> findByTermIdAndVersionWithJoin();
}
