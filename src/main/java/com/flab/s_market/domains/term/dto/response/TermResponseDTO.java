package com.flab.s_market.domains.term.dto.response;

import com.flab.s_market.domains.term.domain.Term;

public record TermResponseDTO (
    String title,
    boolean isRequired
    ){

    public static TermResponseDTO from(Term term){
        return new TermResponseDTO(term.getTitle(), term.getIsRequired());
    }
}
