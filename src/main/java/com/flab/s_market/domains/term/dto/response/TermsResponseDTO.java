package com.flab.s_market.domains.term.dto.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder @Getter
public class TermsResponseDTO {
    private String title;
    private List<TermResponseDTO> terms;

    public static TermsResponseDTO convertTermResponseDTOToTermsResponseDTO(String title, List<TermResponseDTO> termList){
        return TermsResponseDTO.builder()
            .title(title)
            .terms(termList)
            .build();
    }
}
