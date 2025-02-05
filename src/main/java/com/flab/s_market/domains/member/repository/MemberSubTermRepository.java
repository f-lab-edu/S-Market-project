package com.flab.s_market.domains.member.repository;

import com.flab.s_market.domains.member.domain.MemberSubTerm;
import com.flab.s_market.domains.member.domain.MemberSubTermId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberSubTermRepository extends JpaRepository<MemberSubTerm, MemberSubTermId> {

}
