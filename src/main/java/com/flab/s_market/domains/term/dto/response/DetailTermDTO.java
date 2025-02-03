package com.flab.s_market.domains.term.dto.response;

import com.flab.s_market.domains.term.domain.SubTerm;

public record DetailTermDTO (
    String url,
    Integer version,
    String title
    ){
    public static DetailTermDTO from(SubTerm subTerm){
        return new DetailTermDTO(subTerm.getUrl(), subTerm.getId().getVersion(), subTerm.getTerm().getTitle());
    }
}
