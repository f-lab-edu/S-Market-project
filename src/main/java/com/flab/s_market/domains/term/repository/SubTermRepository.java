package com.flab.s_market.domains.term.repository;

import com.flab.s_market.domains.term.domain.SubTerm;
import com.flab.s_market.domains.term.domain.SubTermId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubTermRepository extends JpaRepository<SubTerm, SubTermId>, SubTermCustomRepository {

}
