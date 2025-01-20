package com.flab.s_market.domains.term.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AllTermResponseDTO {
    private TermsResponseDTO main; // 주 약관
    private TermsResponseDTO additional; // 추가 약관(신세계 멤버십)

    public static AllTermResponseDTO convertTermsResponseDTOToAllTermResponseDTO(TermsResponseDTO main, TermsResponseDTO additional){
        return AllTermResponseDTO.builder()
            .main(main)
            .additional(additional)
            .build();
    }
}
