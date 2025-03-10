package com.flab.s_market.domains.term.repository;

import com.flab.s_market.domains.term.domain.SubTerm;
import com.flab.s_market.domains.term.domain.SubTermId;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubTermRepository extends JpaRepository<SubTerm, SubTermId>, SubTermCustomRepository {
    Optional<SubTerm> findByTermIdAndVersion(Long termId, Integer version);
    // 보통은 엔티티로 반환해서 서비스에서 변환, 더 다양하게 쓸 수 있도록 하기 위함.
}
