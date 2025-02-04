package com.flab.s_market.domains.user.dto.request;

import com.flab.s_market.domains.term.domain.SubTerm;

public record AgreedTermDTO (
    String title,
    Integer version
    ){
}