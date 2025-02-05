package com.flab.s_market.domains.member.repository;

import com.flab.s_market.domains.member.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>, MemberCustomRepository {
    boolean existsByEmail(String email);
    Optional<Member> findByEmail(String email);
}
