package com.flab.s_market.domains.member.domain;

import com.flab.s_market.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberSubTerm extends BaseEntity {
    @EmbeddedId
    private MemberSubTermId id;

    // 동의한것만 저장? 안한것도 저장? 이건 기획자랑 얘기하면 됨
    @Column(nullable = false)
    private boolean agree;

    @MapsId("memberId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

}
