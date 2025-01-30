package com.flab.s_market.domains.term.dto.response;

import java.util.List;

public record TermsResponseDTO (
    String title,
    List<TermResponseDTO> terms
    ){

    public static TermsResponseDTO from(String title, List<TermResponseDTO> termList){
        return new TermsResponseDTO(title, termList);
    }
}
