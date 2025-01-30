package com.flab.s_market.domains.term.repository;

import com.flab.s_market.domains.term.domain.SubTerm;
import java.util.List;
import java.util.Optional;

public interface TermCustomRepository {
    Optional<SubTerm> findByTitleAndVersionWithJoin(String title, Integer version);
    List<SubTerm> findByTermIdAndVersionWithJoin();
}
