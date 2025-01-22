package com.flab.s_market.domains.user.repository;

import com.flab.s_market.domains.user.domain.UserSubTerm;
import com.flab.s_market.domains.user.domain.UserSubTermId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSubTermRepository extends JpaRepository<UserSubTerm, UserSubTermId> {

}
