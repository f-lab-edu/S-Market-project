package com.flab.s_market.user.repository;

import com.flab.s_market.user.domain.Term;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TermRepository extends JpaRepository<Term, Long> {
    List<Term> findByDetailAndVersion(boolean detail, double version);
}
