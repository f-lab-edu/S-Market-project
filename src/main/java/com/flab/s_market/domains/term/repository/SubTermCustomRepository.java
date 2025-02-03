package com.flab.s_market.domains.term.repository;

import com.flab.s_market.domains.term.domain.SubTerm;
import java.util.Optional;

public interface SubTermCustomRepository {
    Optional<SubTerm> findByTermIdAndVersion(Long termId, Integer version);
}
