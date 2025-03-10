package com.flab.s_market.domains.term.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubTermId implements Serializable {
    @Column(nullable = false)
    private Integer version;

    @Column(nullable = false)
    private Long termId;
}
