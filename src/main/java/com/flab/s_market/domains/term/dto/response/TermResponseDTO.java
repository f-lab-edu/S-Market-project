package com.flab.s_market.domains.term.dto.response;

import com.flab.s_market.domains.term.domain.Term;
import lombok.Builder;
import lombok.Getter;

@Builder @Getter
public class TermResponseDTO {
    private String title;
    private boolean isRequired;

    public static TermResponseDTO of(Term term){
        return TermResponseDTO.builder()
            .title(term.getTitle())
            .isRequired(term.getIsRequired())
            .build();
    }
}
