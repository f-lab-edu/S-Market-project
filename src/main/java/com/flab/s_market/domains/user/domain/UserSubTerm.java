package com.flab.s_market.domains.user.domain;

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
public class UserSubTerm extends BaseEntity {
    @EmbeddedId
    private UserSubTermId id;

    // 동의한것만 저장? 안한것도 저장? 이건 기획자랑 얘기하면 됨
    @Column(nullable = false)
    private boolean agree;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}
